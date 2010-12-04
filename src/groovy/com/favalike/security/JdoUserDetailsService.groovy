package com.favalike.security

import com.favalike.util.StringUtils
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.SpringSecurityMessageSource
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class JdoUserDetailsService implements UserDetailsService {
    static final Log log = LogFactory.getLog(JdoUserDetailsService)
	def userService
    MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor()

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        if(StringUtils.isBlank(username)) {
            log.error "Username was empty. Authentication failed."
            throw new BadCredentialsException(messages.getMessage("login.username.blank", [] as Object[], "Username may not be empty"), username)
        }

        def user = null

        try {
            user = userService.findUserByUsername(username)
        }
        catch(Exception e) {
            log.error "Problem retrieving user from database", e
            throw new DataRetrievalFailureException(messages.getMessage("login.database.error", [] as Object[], "User could not be retrieved from database. Please try later"))
        }

        if(user == null) {
            log.error "User for username '${username}' not found in database. Authentication failed."
            throw new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.notFound", [username] as Object[], "Username {0} not found"), username)
        }

        if(log.infoEnabled) {
            log.info "Found user for username '${username}': ${user}"
        }

        if(!user.confirmed) {
            log.error "User for username '${username}' is not confirmed yet. Authentication failed."
            throw new BadCredentialsException(messages.getMessage("login.unconfirmed", [] as Object[], "User not confirmed. Please check your emails"), username)
        }

        def grantedAuthorities = []

        user.authorities.each { role ->
            grantedAuthorities << new GrantedAuthorityImpl(role.authority)
        }

        if(grantedAuthorities.size() == 0) {
            log.error "User needs to have at least one granted authority!"
            throw new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.noAuthority", [username] as Object[], "User {0} has no GrantedAuthority"), username)
        }

        if(log.infoEnabled) {
            log.info "User found for username '${username}'."
        }

        return new User(user.username, user.password, user.enabled, true, true, true, grantedAuthorities)
    }
}

