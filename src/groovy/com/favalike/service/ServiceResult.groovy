package com.favalike.service

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Aug 28, 2010
 * Time: 2:00:44 PM
 * To change this template use File | Settings | File Templates.
 */
class ServiceResult {
    final boolean success
    final String message

    ServiceResult(success, message) {
        this.success = success
        this.message = message
    }

    public String toString ( ) {
        return "ServiceResult{" +
               "success=" + success +
               ", message='" + message + '\'' +
               '}' ;
    }
}
