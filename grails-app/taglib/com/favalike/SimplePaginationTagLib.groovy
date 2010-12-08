package com.favalike

import com.favalike.util.PaginationHelper
import com.favalike.util.StringUtils

class SimplePaginationTagLib {
    static namespace = "fav"

    def simplePagination = { attrs, body ->
        def total = attrs.total
        def offset = attrs.offset
        def max = attrs.max ? attrs.max : 10
        def prev = attrs.prev ? attrs.prev : '&lsaquo;&lsaquo; Prev'
        def next = attrs.next ? attrs.next : 'Next &rsaquo;&rsaquo;'
        def function = attrs.function
        def parameters = attrs.parameters ? attrs.parameters : []

        if(total > 0) {
            def firstRecordNumber = PaginationHelper.getFirstRecordNumber(offset)
            def lastRecordNumber = PaginationHelper.getLastRecordNumber(total, offset, max)
            def previousLink = createPreviousLink(hasPreviousRecords(offset, max), prev, offset, max, function, parameters)
            def nextLink = createNextLink(hasNextRecords(offset, max, total), next, offset, max, function, parameters)
            out << render(template: '/taglib/simple_pagination', model: [total: total, max: max, firstRecordNumber: firstRecordNumber, lastRecordNumber: lastRecordNumber, previousLink: previousLink, nextLink: nextLink])
        }
    }

    def getPreviousOffset(offset, max) {
        offset - max
    }

    def hasPreviousRecords(offset, max)
    {
        def previousOffset = getPreviousOffset(offset, max)
        previousOffset >= 0
    }

    def getNextOffset(offset, max) {
        offset + max
    }

    def hasNextRecords(offset, max, total)
    {
        def nextOffset = getNextOffset(offset, max)
        nextOffset < total
    }

    def createPreviousLink(active, label, offset, max, function, parameters)
    {
        createLink(active, label, getPreviousOffset(offset, max), max, function, parameters)
    }

    def createNextLink(active, label, offset, max, function, parameters)
    {
        createLink(active, label, getNextOffset(offset, max), max, function, parameters)
    }

    def createLink(active, label, offset, max, function, parameters)
    {
        if(!active) {
            "<div style='color: #999'>${label}</div>"
        }
        else {
            def functionParameters = parameters.size() > 0 ? StringUtils.join(parameters, ",") << "," : ""
            "<a href='javascript:void(0);' onclick='javascript:${function}(${functionParameters}${offset},${max})'>${label}</a>"
        }
    }
}
