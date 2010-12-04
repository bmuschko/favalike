package com.favalike.http

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Sep 12, 2010
 * Time: 10:13:08 AM
 * To change this template use File | Settings | File Templates.
 */
class HttpResponse {
    String responseBody
    int statusCode
    String reasonPhrase
    String statusLine
    String errorMessage

    boolean isOkResponse() {
        statusCode == 200
    }

    @Override
    public String toString ( ) {
        return "HttpResponse{" +
               "responseBody='" + responseBody + '\'' +
               ", statusCode=" + statusCode +
               ", reasonPhrase='" + reasonPhrase + '\'' +
               ", statusLine='" + statusLine + '\'' +
               ", errorMessage='" + errorMessage + '\'' +
               '}' ;
    }
}
