package com.favalike.util

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Sep 6, 2010
 * Time: 11:43:48 AM
 * To change this template use File | Settings | File Templates.
 */
final class PaginationHelper {
    private PaginationHelper() {
    }

    static getFirstRecordNumber(offset) {
        offset + 1
    }

    static getLastRecordNumber(total, offset, max) {
        if((total - offset) < max)
        {
            return offset + (total - offset)
        }

        if(total < max)
        {
            return total
        }

        offset + max
    }
}
