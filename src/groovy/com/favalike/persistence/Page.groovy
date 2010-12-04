package com.favalike.persistence

import com.favalike.util.PaginationHelper

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Aug 30, 2010
 * Time: 10:09:57 PM
 * To change this template use File | Settings | File Templates.
 */
class Page {
    static final long DEFAULT_OFFSET = 0
    static final long DEFAULT_MAX = 10

    final offset
    final max
    final total
    final records

    Page(offset, max, total, records) {
        this.offset = offset
        this.max = max
        this.total = total
        this.records = records
    }

    def getFirstRecordNumber() {
        PaginationHelper.getFirstRecordNumber(offset)
    }

    def getLastRecordNumber() {
        PaginationHelper.getLastRecordNumber(total, offset, max)
    }

    public String toString () {
        return "Page{" +
               "offset=" + offset +
               ", max=" + max +
               ", total=" + total +
               ", records=" + records +
               '}' ;
    }
}
