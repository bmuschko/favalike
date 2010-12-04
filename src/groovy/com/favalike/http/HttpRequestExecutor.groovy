package com.favalike.http

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Sep 12, 2010
 * Time: 9:34:43 AM
 * To change this template use File | Settings | File Templates.
 */
interface HttpRequestExecutor {
    HttpResponse sendGetRequest(String url)
}
