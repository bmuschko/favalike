package com.favalike.persistence

import javax.jdo.Query

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Aug 30, 2010
 * Time: 9:28:30 PM
 * To change this template use File | Settings | File Templates.
 */
class JdoUtil {
    private JdoUtils() {
    }

    static closeAll(Query query) {
        if(query) {
            query.closeAll()
        }
    }

    static getFirstElement(elements) {
        def element = null

        if(elements != null && elements.size() > 0) {
            element = elements.get(0)
        }

        element
    }
}
