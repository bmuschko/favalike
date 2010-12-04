package com.favalike.persistence

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Sep 23, 2010
 * Time: 7:17:34 AM
 * To change this template use File | Settings | File Templates.
 */
class PersistentObjectNotFoundException extends RuntimeException {
    public PersistentObjectNotFoundException() {
        super();
    }

    public PersistentObjectNotFoundException(String message) {
        super(message);
    }

    public PersistentObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }    
}
