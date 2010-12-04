package com.favalike

import com.google.appengine.api.datastore.KeyFactory
import com.favalike.persistence.Page

class BookmarkController {
	def bookmarkService

    def findAllByUser = {
        log.info "Finding all bookmarks by user"

        def offset = determineOffset(params)
        def max = determineMax(params)

        def bookmarks = bookmarkService.findAllByUserInRange(offset, max)

        if(log.debugEnabled) {
            log.debug "Retrieved bookmarks page: ${bookmarks}"
        }

        render view: 'show', model: [bookmarks: bookmarks, ajaxFunctionName: 'getBookmarksForUser']
    }

    def findAllByUserAndFolderId = {
        log.info "Finding all bookmarks by user and folder ID"

        def offset = determineOffset(params)
        def max = determineMax(params)

		def bookmarks = bookmarkService.findAllByUserAndFolderIdInRange(params.folderId, offset, max)

        if(log.debugEnabled) {
            log.debug "Retrieved bookmarks page: ${bookmarks}"
        }

        render view: 'show', model: [bookmarks: bookmarks, ajaxFunctionName: 'getBookmarksForUserAndFolder', ajaxFunctionParams: ["\"${params.folderId}\""]]
    }

    def findAllByUserAndTagId = {
        log.info "Finding all bookmarks by user and tag ID"

        def offset = determineOffset(params)
        def max = determineMax(params)

        def bookmarks = bookmarkService.findAllByUserAndTagIdInRange(params.tagId, offset, max)

        if(log.debugEnabled) {
            log.debug "Retrieved bookmarks page: ${bookmarks}"
        }

        render view: 'show', model: [bookmarks: bookmarks, ajaxFunctionName: 'getBookmarksForUserAndTag', ajaxFunctionParams: ["\"${params.tagId}\""]]
    }

    def findAllByUserAndHasTag = {
        log.info "Finding all bookmarks by user and has tag"

        def offset = determineOffset(params)
        def max = determineMax(params)

        def bookmarks = bookmarkService.findAllByUserAndHasTagInRange(offset, max)

        if(log.debugEnabled) {
            log.debug "Retrieved bookmarks page: ${bookmarks}"
        }

        render view: 'show', model: [bookmarks: bookmarks, ajaxFunctionName: 'getBookmarksForUserAndHasTag']
    }

    def findAllByUserAndFavorite = {
        log.info "Finding all bookmarks by user and favorite"

        def offset = determineOffset(params)
        def max = determineMax(params)

        def bookmarks = bookmarkService.findAllByUserAndFavoriteInRange(offset, max)

        if(log.debugEnabled) {
            log.debug "Retrieved bookmarks page: ${bookmarks}"
        }

        render view: 'show', model: [bookmarks: bookmarks, ajaxFunctionName: 'getBookmarksForUserAndFavorite']
    }

    def findByBookmarkId = {
        log.info "Finding bookmark by bookmark ID"
        def bookmark = bookmarkService.findById(params.bookmarkId)
        renderBookmarkJson(bookmark)
    }

	def add = {
		log.info "Adding bookmark"
		def newBookmark = new Bookmark(params)
		bookmarkService.save(newBookmark, params.folderId)
		renderBookmarkJson(newBookmark)
	}
	
	def delete = {
        if(log.infoEnabled) {
		    log.info "Deleting bookmark with ID '${params.id}'"
		}
      
        bookmarkService.delete(params.id)
		response.setStatus(200) 
		render "success"
  	}

    def update = {
        log.info "Updating bookmark"
		def bookmark = bookmarkService.updateBookmark(params.id, params.name, params.location, params.commaSeparatedTags, params.description)
		renderBookmarkJson(bookmark)
    }

    def favorite = {
        log.info "Toggling bookmark favorite"
        bookmarkService.updateBookmarkFavorite(params.id, params.boolean('favorite'))
        render "success"
    }

    def metaData = {
        log.info "Retrieving bookmark meta data"
        def siteMetaData = bookmarkService.retrieveBookmarkData(params.url)
        renderSiteMetaDataJson(siteMetaData)
    }

    def renderBookmarkJson(bookmarkData) {
        render(contentType:'text/json') {
			bookmark(id: KeyFactory.keyToString(bookmarkData.key), name: bookmarkData.name, location: bookmarkData.location?.value, commaSeparatedTags: bookmarkData.commaSeparatedTags, description: bookmarkData?.description.value)
		}
    }

    def renderBookmarksJson(bookmarkData) {
        render(contentType:'text/json') {
            bookmarks {
                bookmarkData.each {
                    bookmark(id: KeyFactory.keyToString(it.key), name: it.name, location: it.location.value, description: it.description?.value, commaSeparatedTags: it.commaSeparatedTags)
                }
            }
		}
    }

    def renderSiteMetaDataJson(siteMetaData) {
        render(contentType:'text/json') {
            bookmark(title: siteMetaData.title, description: siteMetaData.description, faviconUrl: siteMetaData.faviconUrl)
        }
    }

    def determineOffset(params) {
        params.offset ? params.long('offset') : Page.DEFAULT_OFFSET
    }

    def determineMax(params) {
        params.max ? params.long('max') : Page.DEFAULT_MAX
    }
}