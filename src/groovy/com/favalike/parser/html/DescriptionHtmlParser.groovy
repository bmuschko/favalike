package com.favalike.parser.html

import org.apache.commons.lang.StringEscapeUtils
import org.htmlparser.filters.TagNameFilter

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Sep 17, 2010
 * Time: 7:40:20 PM
 * To change this template use File | Settings | File Templates.
 */
class DescriptionHtmlParser extends BaseHtmlParser implements HtmlParser {
    def parse(html) {
        def description = null
        def parser = createParser(html)
        def metaFilter = new TagNameFilter("META");
        def metaTagNodes = parser.parse(metaFilter)
        def metaTagNodesIterator = metaTagNodes.elements()

        while(metaTagNodesIterator.hasMoreNodes()) {
            def node = metaTagNodesIterator.nextNode()
            def name = node.getAttribute("name")

            if(name == "description") {
                def content = node.getAttribute("content")

                if(content != null) {
                    description = StringEscapeUtils.unescapeHtml(content).trim()
                    break;
                }
            }
        }

        description
    }
}
