package com.favalike.security

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.security.authentication.RememberMeAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

class SpringSecurityAuthenticationHelper {
	static final Log log = LogFactory.getLog(SpringSecurityAuthenticationHelper)
    def userDetailsService
    def authenticationManager

    def getCurrentUser() {
        def authentication = SecurityContextHolder.getContext().getAuthentication()

        if(authentication != null && authentication.principal instanceof UserDetails) {
            return authentication.principal
        }

        return null
    }

    def isLoggedIn() {
        getCurrentUser() != null
    }

    def isLoggedIn(user) {
        return user != null
    }

    def autoLogin(user) {
        try {
            if(!isLoggedIn()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername())
                Authentication authentication = new RememberMeAuthenticationToken(userDetails.getUsername(), userDetails, userDetails.getAuthorities())
                SecurityContextHolder.getContext().setAuthentication(authentication)
            }
        }
        catch(Exception e) {
            log.error "Automatic login didn't work for application user '${user}'", e
        }
    }

    def authenticate(username, password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password))
        SecurityContextHolder.getContext().setAuthentication(authentication)
    }
}

