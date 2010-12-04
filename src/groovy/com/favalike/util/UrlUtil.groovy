package com.favalike.util

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Aug 18, 2010
 * Time: 7:34:10 PM
 * To change this template use File | Settings | File Templates.
 */
class UrlUtil {
    private UrlUtil
    {}

    static getHostUrl(request) {
        def scheme = request.getScheme();
        def serverName = request.getServerName();
        def portNumber = request.getServerPort();
        def webApplicationUrl = scheme << "://" << serverName
      
        if(portNumber != 80) {
            webApplicationUrl << ":" << portNumber
        }
      
        webApplicationUrl
    }

    static getHostUrl(URL url) {
        def protocol = url.getProtocol()
        def host = url.getHost()
        def port = url.getPort()

        def webApplicationUrl = protocol << "://" << host

        if(port != 80 && port != -1) {
            webApplicationUrl << ":" << port
        }

        webApplicationUrl
    }
}
