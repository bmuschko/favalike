package com.favalike.parser.html

import org.htmlparser.filters.TagNameFilter

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Sep 17, 2010
 * Time: 7:41:06 PM
 * To change this template use File | Settings | File Templates.
 */
class FaviconUrlHtmlParser extends BaseHtmlParser implements HtmlParser {
    def parse(html) {
        def faviconUrl = null
        def parser = createParser(html)
        def linkFilter = new TagNameFilter("LINK");
        def linkTagNodes = parser.parse(linkFilter)
        def linkTagNodesIterator = linkTagNodes.elements()

        while(linkTagNodesIterator.hasMoreNodes()) {
            def node = linkTagNodesIterator.nextNode()
            def type = node.getAttribute("type")
            def rel = node.getAttribute("rel")

            if(type?.equals("image/ico") || rel?.equalsIgnoreCase("shortcut icon") || rel?.equalsIgnoreCase("icon")) {
                def href = node.getAttribute("href")

                if(href != null) {
                    faviconUrl = href
                    break;
                }
            }
        }

        faviconUrl
    }
}
