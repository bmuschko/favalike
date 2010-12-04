package com.favalike.parser.html

import com.favalike.service.SiteMetaData
import com.favalike.util.StringUtils
import com.favalike.util.UrlUtil

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Sep 17, 2010
 * Time: 7:43:27 PM
 * To change this template use File | Settings | File Templates.
 */
class SiteMetaDataBuilder {
    def httpRequestExecutor
    def titleHtmlParser
    def descriptionHtmlParser
    def faviconUrlHtmlParser

    def build(url) {
        def httpResponse = httpRequestExecutor.sendGetRequest(url)
        def siteMetaData = new SiteMetaData()
        siteMetaData.title = titleHtmlParser.parse(httpResponse.responseBody)
        siteMetaData.description = descriptionHtmlParser.parse(httpResponse.responseBody)
        siteMetaData.faviconUrl = determineFaviconUrl(url, httpResponse.responseBody)
        siteMetaData
    }

    def determineFaviconUrl(url, html) {
        def faviconUrl = faviconUrlHtmlParser.parse(html)
        def hostUrl = UrlUtil.getHostUrl(new URL(url))

        // Check if Favicon is in default location
        if(faviconUrl == null) {
            def defaultFaviconUrl = "${hostUrl}/favicon.ico"

            if(isFaviconExisting(defaultFaviconUrl)) {
                faviconUrl = defaultFaviconUrl
            }
        }
        else {
            // If path is relative then append host URL
            def parsedFaviconUrl = !faviconUrl.startsWith("http") ? buildFullFaviconUrl(hostUrl, faviconUrl).toString() : faviconUrl
            faviconUrl = isFaviconExisting(parsedFaviconUrl) ? parsedFaviconUrl : null
        }

        faviconUrl
    }

    def buildFullFaviconUrl(hostUrl, faviconUrl) {
        def fullFaviconUrl = new StringBuilder()
        fullFaviconUrl.append(hostUrl)

        if(!faviconUrl.startsWith("/")) {
            fullFaviconUrl.append("/")
        }
        else if(faviconUrl.startsWith("./")) {
            faviconUrl.replace("./", "/")
        }

        fullFaviconUrl.append(faviconUrl)
        fullFaviconUrl
    }

    def isFaviconExisting(faviconUrl) {
        def httpResponse = httpRequestExecutor.sendGetRequest(faviconUrl)
        httpResponse.isOkResponse() && !StringUtils.isBlank(httpResponse.responseBody)
    }
}
