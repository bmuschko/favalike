package com.favalike

import com.favalike.service.ServiceResult
import org.apache.commons.codec.digest.DigestUtils
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.transaction.support.TransactionCallback

class UserService extends JdoService {
    def passwordEncoder
    def mailSender
    def messageSource
    def adminEmail = ConfigurationHolder.config.admin.email
    static final DEFAULT_ROOT_FOLDER = 'All Bookmarks'

    def findUserByUsername(username) {
        def user

        transactionTemplate.execute( { status ->
            def users = jdoTemplate.find(User, "username == usernameParam", "String usernameParam", [username] as Object[])

            if(users != null && users.size() > 0) {
                user = users.get(0)
            }
        } as TransactionCallback)

        user
    }

    def findUserByToken(token) {
        def user

        transactionTemplate.execute( { status ->
            def users = jdoTemplate.find(User, "confirmed == confirmedParam && token == tokenParam", "java.lang.Boolean confirmedParam, String tokenParam", [false, token] as Object[])

            if(users != null && users.size() > 0) {
                user = users.get(0)
            }
        } as TransactionCallback)

        user
    }

    def createUser(user, webApplicationUrl) {
        addDefaultPermission(user)
      
        if(user.validate()) {
            def existingUser = findUserByUsername(user.username)

            if(existingUser) {
                // Username has to be unique
                user.errors.rejectValue('username', 'user.username.unique', [user.username] as Object[], 'User with username {0} already exists')
            }
            else {
                // Assign encoded password
                user.password = passwordEncoder.encodePassword(user.password, null)

                // Generate token
                user.token = DigestUtils.md5Hex(UUID.randomUUID().toString())

                // Set creation date
                user.created = new Date()

                if(log.debugEnabled) {
                    log.debug "Inserting user: ${user}"
                }

                // Persist user
                jdoTemplate.makePersistent(user)

                // Create default root folder
                def folder = new Folder(name: DEFAULT_ROOT_FOLDER, user: user)
                jdoTemplate.makePersistent(folder)

                // Send email
                sendRegistrationEmail(user, webApplicationUrl)
            }
        }

        user
    }

    def resendRegistrationEmail(username, webApplicationUrl) {
        def user = findUserByUsername(username)

        if(user) {
            sendRegistrationEmail(user, webApplicationUrl)
        }
    }

    def confirmUser(token) {
        if(token) {
            def user = findUserByToken(token)

            if(log.debugEnabled) {
                log.debug "Found user for token '${token}': ${user}"
            }

            if(user) {
                user.confirmed = true
                jdoTemplate.makePersistent(user)
                return new ServiceResult(true, messageSource.getMessage("user.confirmation.success", [] as Object[], new Locale("en", "US")))
            }
            else {
                return new ServiceResult(false, messageSource.getMessage("user.confirmation.user.not.found", [] as Object[],  new Locale("en", "US")))
            }
        }

        return new ServiceResult(false, messageSource.getMessage("user.confirmation.token.blank", [] as Object[],  new Locale("en", "US")))
    }

    def addDefaultPermission(user) {
        user.enabled = true
        def role = new Role(authority: 'ROLE_USER')
        user.authorities << role
        user
    }

    def getRegistrationEmailText(user, webApplicationUrl) {
        def confirmationUrl = webApplicationUrl << "/user/confirm?token=" << user.token
        "Hi ${user.firstname} ${user.lastname},<br><br>Start using Favalike, for free, here:<br><br><a href='${confirmationUrl}'>${confirmationUrl}</a><br><br>With the application you can:<br><ul><li>Access your bookmarks from everywhere<li>Mark your favorite bookmarks<li>Export your bookmarks</ul>Thanks,<br><br>The Favalike Admin"
    }

    def sendRegistrationEmail(user, webApplicationUrl) {
        def emailBody = getRegistrationEmailText(user, webApplicationUrl)
        mailSender.sendMail(adminEmail, user.username, "Favalike Registration", emailBody)
    }
}
