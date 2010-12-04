package com.favalike.parser.html

import org.apache.commons.lang.StringEscapeUtils
import org.htmlparser.filters.TagNameFilter

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Sep 17, 2010
 * Time: 7:37:30 PM
 * To change this template use File | Settings | File Templates.
 */
class TitleHtmlParser extends BaseHtmlParser implements HtmlParser {
    def parse(html) {
        def title = null
        def parser = createParser(html)
        def titleFilter = new TagNameFilter("TITLE");
        def titleTagNodes = parser.parse(titleFilter)

        if(titleTagNodes.size() > 0) {
            title = StringEscapeUtils.unescapeHtml(titleTagNodes.elementAt(0).toPlainTextString()).trim()
        }

        title
    }
}
