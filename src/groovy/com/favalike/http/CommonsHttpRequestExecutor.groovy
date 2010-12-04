package com.favalike.http

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.conn.ClientConnectionManager
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.params.BasicHttpParams
import org.apache.http.params.CoreConnectionPNames
import org.apache.http.params.CoreProtocolPNames
import org.apache.http.params.HttpParams
import org.apache.http.util.EntityUtils

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Sep 12, 2010
 * Time: 9:37:45 AM
 * To change this template use File | Settings | File Templates.
 */
class CommonsHttpRequestExecutor implements HttpRequestExecutor {
    static final Log log = LogFactory.getLog(CommonsHttpRequestExecutor)
    final Integer timeout

    CommonsHttpRequestExecutor(timeout) {
        this.timeout = timeout
    }

    def HttpResponse sendGetRequest(String url) {
        HttpGet httpGet = new HttpGet(escapeUrl(url));

        if(log.debugEnabled) {
            log.debug("Sending GET request to URL '${httpget.getURI()}'")
        }

        executeHttpRequest(httpGet);
    }

    def escapeUrl(url) {
        url = url.replaceAll(" ", "%20")
        url
    }

    def executeHttpRequest(HttpUriRequest httpUriRequest) {
        HttpResponse httpResponse = new HttpResponse()
        HttpClient httpclient = getDefaultHttpClient()

        try {
            def response = httpclient.execute(httpUriRequest)
            populateResponseData(response, httpResponse)
        }
        catch(Exception e) {
            log.error("Error accessing site for url '${httpUriRequest.getURI()}'", e)
            httpResponse.errorMessage = e.message
            httpUriRequest.abort()
        }
        finally {
            httpclient.getConnectionManager().shutdown();
        }

        httpResponse
    }

    def getDefaultHttpClient() {
        ClientConnectionManager clientConnectionManager = new GAEConnectionManager();
        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
        params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");    
        new DefaultHttpClient(clientConnectionManager, params)
    }

    def populateResponseData(actualResponse, httpResponse) {
        httpResponse.statusCode = actualResponse.statusLine.statusCode
        httpResponse.reasonPhrase = actualResponse.statusLine.reasonPhrase
        httpResponse.statusLine = actualResponse.statusLine.toString()
        HttpEntity entity = actualResponse.getEntity();
      
        if(entity != null) {
            byte[] content = EntityUtils.toByteArray(entity)
            Header header = entity.getContentType() 
            String charset = parseCharset(header)
            httpResponse.responseBody = new String(content, charset)
        }
    }

    private String parseCharset(contentType) {
        String charset = "UTF-8"

        if(contentType != null) {
            def values = contentType.getValue().split(";")
            for(String value : values) {
                value = value.trim()

                if(value.startsWith("charset=")) {
                    charset = value.split("=")[1]
                }
            }
        }

        charset
    }
}
