package com.favalike

class BaseService extends JdoService {
    def userService
    def authenticationHelper

    def getLoggedInUser() {
        def username = authenticationHelper.getCurrentUser().username
        userService.findUserByUsername(username)
    }
}

