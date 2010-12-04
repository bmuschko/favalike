package com.favalike.service

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: Sep 12, 2010
 * Time: 11:08:32 AM
 * To change this template use File | Settings | File Templates.
 */
class SiteMetaData {
    String title
    String description
    String faviconUrl

    @Override
    public String toString ( ) {
        return "SiteMetaData{" +
               "title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", faviconUrl='" + faviconUrl + '\'' +
               '}' ;
    }
}
