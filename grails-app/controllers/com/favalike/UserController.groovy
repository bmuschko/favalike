package com.favalike

import com.favalike.util.UrlUtil
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class UserController {
    def userService

    def index = {
        render view: 'index'
    }

    def signup = {
        log.info "Signing up new user"

        def user = new User(params)
        def webApplicationUrl = UrlUtil.getHostUrl(request)
        userService.createUser(user, webApplicationUrl)

        if(user.hasErrors()) {
            render view: 'index', model: [user: user]
        }
        else {
            render view: 'signup_message', model: [username: user.username, email: ConfigurationHolder.config.admin.email]
        }
    }

    def resendmail = {
        log.info "Resending user registration email"
      
        if(params.username) {
            def webApplicationUrl = UrlUtil.getHostUrl(request)
            userService.resendRegistrationEmail(params.username, webApplicationUrl)
        }

        render view: 'signup_message', model: [username: params.username]
    }

    def confirm = {
        log.info "Confirming up new user"

        def serviceResult = userService.confirmUser(params.token)

        render view: 'confirmation_message', model: [result: serviceResult]
    }

    def ping = {
        render "User Ping: ${new Date().toString()}"
    }
}
