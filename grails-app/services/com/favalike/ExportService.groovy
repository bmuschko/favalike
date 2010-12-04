package com.favalike

import groovy.xml.MarkupBuilder

class ExportService extends BaseService {
    final DATE_FORMAT = 'MM/dd/yyyy HH:mm:ss'
    def folderService

    def exportToHtml() {
        def user = getLoggedInUser()

        def writer = new StringWriter()
        def builder = new MarkupBuilder(writer)

        builder.meta('http-equiv': 'Content-Type', content: 'text/html; charset=UTF-8') {}
        builder.title('Bookmarks') {}
        builder.h1("Bookmarks for ${user.firstname} ${user.lastname}") {}

        def rootFolder = user.folder

        builder.dl() {
            builder.dt() {
                builder.h3(rootFolder.name) {}
            }

            if(rootFolder.description) {
                builder.dd(rootFolder.description.value) {}
            }

            buildRecursiveHtmlMarkup(builder, rootFolder)
        }

        writer.toString()
    }

    def buildRecursiveHtmlMarkup(builder, currentFolder) {
        def folders = folderService.findAllByFolder(currentFolder)

        builder.dl() {
            builder.p() {}
          
            currentFolder.bookmarks.each { bookmark ->
                builder.dt() {
                    builder.a(href: bookmark.location, creation_date: bookmark.created.format(DATE_FORMAT), bookmark.name) {}
                }

                if(bookmark.description) {
                    builder.dd(bookmark.description.value) {}
                }
            }

            folders.each { folder ->
                builder.dt() {
                    builder.h3(folder.name) {}
                }

                if(folder.description) {
                    builder.dd(folder.description.value) {}
                }

                buildRecursiveHtmlMarkup(builder, folder)
            }
        }
    }

    def exportToXml() {
        def user = getLoggedInUser()
        def rootFolder = user.folder

        def writer = new StringWriter()
        def builder = new MarkupBuilder(writer)
        builder.mkp.xmlDeclaration(version: "1.0", encoding: "UTF-8")
        
        builder.bookmarks() {
            builder.user("${user.firstname} ${user.lastname}") {}
            builder.name(rootFolder.name) {}

            if(rootFolder.description) {
                builder.description(rootFolder.description.value) {}
            }

            buildRecursiveXmlMarkup(builder, rootFolder)
        }

        writer.toString()
    }

    def buildRecursiveXmlMarkup(builder, currentFolder) {
        def folders = folderService.findAllByFolder(currentFolder)

        currentFolder.bookmarks.each { bookmark ->
            builder.bookmark() {
                builder.name(bookmark.name) {}
                builder.location(bookmark.location) {}
                builder.tags(bookmark.commaSeparatedTags) {}
                builder.created(bookmark.created.format(DATE_FORMAT)) {}

                if(bookmark.description) {
                    builder.description(bookmark.description.value) {}
                }
            }
        }

        folders.each { folder ->
            builder.folder() {
                builder.name(folder.name)

                if(folder.description) {
                    builder.description(folder.description.value) {}
                }

                buildRecursiveXmlMarkup(builder, folder)
            }
        }
    }
}
