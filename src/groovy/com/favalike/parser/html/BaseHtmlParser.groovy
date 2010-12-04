package com.favalike.parser.html

import org.htmlparser.Parser
import org.htmlparser.lexer.Lexer
import org.htmlparser.lexer.Page

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Sep 17, 2010
 * Time: 7:38:32 PM
 * To change this template use File | Settings | File Templates.
 */
class BaseHtmlParser {
    def createParser(String html) {
        def lexer = new Lexer(new Page(html, "UTF-8"))
        new Parser(lexer)
    }
}
