import org.codehaus.groovy.grails.commons.ConfigurationHolder

// Place your Spring DSL code here
beans = {
    // Security
    xmlns security: 'http://www.springframework.org/schema/security'

    jdoUserDetailsService(com.favalike.security.JdoUserDetailsService) {
        userService = ref('userService')
    }

    authenticationHelper(com.favalike.security.SpringSecurityAuthenticationHelper) {
        userDetailsService = ref('jdoUserDetailsService')
        authenticationManager = ref('authenticationManager')
    }

    passwordEncoder(org.springframework.security.authentication.encoding.Md5PasswordEncoder)

    security.'http'('auto-config': true, 'access-denied-page': '/user/index') {
        security.'intercept-url'('pattern': '/user/ping', 'filters': 'none')
        security.'intercept-url'('pattern': '/user/index*', 'filters': 'none')
        security.'intercept-url'('pattern': '/user/signup', 'filters': 'none')
        security.'intercept-url'('pattern': '/user/resendmail*', 'filters': 'none')
        security.'intercept-url'('pattern': '/user/confirm*', 'filters': 'none')
        security.'intercept-url'('pattern': '/favicon.ico', 'filters': 'none')
        security.'intercept-url'('pattern': '/**/*.html', 'access': 'ROLE_USER')
        security.'intercept-url'('pattern': '/**/*.gsp', 'access': 'ROLE_USER')
        security.'intercept-url'('pattern': '/**', 'access': 'ROLE_USER')
        security.'form-login'('authentication-failure-url': '/user/index?login_error=1', 'default-target-url': '/', 'login-page': '/user/index')
        security.'remember-me'('key': '63f9l2ad9834sd2na')
        security.'logout'('logout-success-url': '/user/index')
    }

    security.'authentication-manager'('alias': 'authenticationManager') {
        security.'authentication-provider'('user-service-ref': 'jdoUserDetailsService') {
            security.'password-encoder'('hash': 'md5')
        }
    }

    //  Application services
    mailSender(com.favalike.mail.JavaMailSender)
    httpRequestExecutor(com.favalike.http.CommonsHttpRequestExecutor, ConfigurationHolder.config.http.request.timeout)

    titleHtmlParser(com.favalike.parser.html.TitleHtmlParser)
    descriptionHtmlParser(com.favalike.parser.html.DescriptionHtmlParser)
    faviconUrlHtmlParser(com.favalike.parser.html.FaviconUrlHtmlParser)

    siteMetaDataBuilder(com.favalike.parser.html.SiteMetaDataBuilder) {
        httpRequestExecutor = ref(httpRequestExecutor)
        titleHtmlParser = ref(titleHtmlParser)
        descriptionHtmlParser = ref(descriptionHtmlParser)
        faviconUrlHtmlParser = ref(faviconUrlHtmlParser)
    }
}