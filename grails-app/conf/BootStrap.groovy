import grails.util.Environment

class BootStrap {
     def messageSource

     def init = { servletContext ->
        if( Environment.getCurrent() == Environment.PRODUCTION ){
            messageSource.basenames = [ 'WEB-INF/grails-app/i18n/messages' ]
            messageSource.clearCache()
        }
     }

     def destroy = {
     }
} 