package com.favalike

import com.favalike.persistence.JdoUtil
import com.favalike.persistence.Page
import com.favalike.security.AuthorizationException
import com.favalike.util.StringUtils
import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Link
import com.google.appengine.api.datastore.Text
import javax.jdo.Query
import org.springframework.transaction.support.TransactionCallback

class BookmarkService extends BaseService {
	def tagService
	def userBookmarkTagService
    def siteMetaDataBuilder

    def findAllByUser() {
        def bookmarks = []

        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            bookmarks = jdoTemplate.find(Bookmark, "userKey == userKeyParam", "com.google.appengine.api.datastore.Key userKeyParam", [user.key] as Object[], "created asc")
        } as TransactionCallback)

        bookmarks
    }

    def findAllByUserInRange(offset, max) {
        def bookmarks = []
        def total

        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            Query query

            try {
                final filter = "userKey == userKeyParam"
                final parameters = "com.google.appengine.api.datastore.Key userKeyParam" 
                query = jdoTemplate.getPersistenceManagerFactory().getPersistenceManager().newQuery(Bookmark)
                query.setFilter(filter)
                query.declareParameters(parameters);
                query.setResult("count(this)")
                total = query.execute(user.key)
                query = jdoTemplate.getPersistenceManagerFactory().getPersistenceManager().newQuery(Bookmark)
                query.setFilter(filter)
                query.declareParameters(parameters);
                query.setOrdering("created asc")
                query.setRange(offset, offset + max)
                bookmarks = query.execute(user.key)
            }
            finally {
                JdoUtil.closeAll(query)
            }
        } as TransactionCallback)

        new Page(offset, max, total, bookmarks)
    }

    def findAllByUserAndFolderId(folderId) {
        def bookmarks = []

        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            Key key = KeyFactory.stringToKey(folderId)
            def folder = jdoTemplate.getObjectById(Folder, key)
            bookmarks = jdoTemplate.find(Bookmark, "userKey == userKeyParam && folder == folderParam", "com.google.appengine.api.datastore.Key userKeyParam, com.favalike.Folder folderParam", [user.key, folder] as Object[], "created asc")
        } as TransactionCallback)

        bookmarks
    }

    def findAllByUserAndFolderIdInRange(folderId, offset, max) {
        def bookmarks = []
        def total

        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            Key key = KeyFactory.stringToKey(folderId)
            def folder = jdoTemplate.getObjectById(Folder, key)
            Query query

            try {
                final filter = "userKey == userKeyParam && folder == folderParam"
                final parameters = "com.google.appengine.api.datastore.Key userKeyParam, com.favalike.Folder folderParam"
                query = jdoTemplate.getPersistenceManagerFactory().getPersistenceManager().newQuery(Bookmark)
                query.setFilter(filter)
                query.declareParameters(parameters);
                query.setResult("count(this)")
                total = query.execute(user.key, folder)
                query = jdoTemplate.getPersistenceManagerFactory().getPersistenceManager().newQuery(Bookmark)
                query.setFilter(filter)
                query.declareParameters(parameters);
                query.setOrdering("created asc")
                query.setRange(offset, offset + max)
                bookmarks = query.execute(user.key, folder)
            }
            finally {
                JdoUtil.closeAll(query)
            }
        } as TransactionCallback)

        new Page(offset, max, total, bookmarks)
    }

    def findAllByUserAndTagId(tagId) {
        def bookmarks = []

        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            Key tagKey = KeyFactory.stringToKey(tagId)
            def userBookmarkTags = jdoTemplate.find(UserBookmarkTag, "tagKey == tagKeyParam && user == userParam", "com.google.appengine.api.datastore.Key tagKeyParam, com.favalike.User userParam", [tagKey, user] as Object[])

            userBookmarkTags.each {
                bookmarks << jdoTemplate.getObjectById(Bookmark, it.bookmarkKey)
            }
        } as TransactionCallback)

        bookmarks
    }

    def findAllByUserAndTagIdInRange(tagId, offset, max) {
        def bookmarks = []
        def total

        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            Key tagKey = KeyFactory.stringToKey(tagId)
            Query query

            try {
                final filter = "tagKey == tagKeyParam && user == userParam"
                final parameters = "com.google.appengine.api.datastore.Key tagKeyParam, com.favalike.User userParam"
                query = jdoTemplate.getPersistenceManagerFactory().getPersistenceManager().newQuery(UserBookmarkTag)
                query.setFilter(filter)
                query.declareParameters(parameters);
                query.setResult("count(this)")
                total = query.execute(tagKey, user)
                query = jdoTemplate.getPersistenceManagerFactory().getPersistenceManager().newQuery(UserBookmarkTag)
                query.setFilter(filter)
                query.declareParameters(parameters);
                query.setOrdering("creationDate asc")
                query.setRange(offset, offset + max)
                def userBookmarkTags = query.execute(tagKey, user)
              
                userBookmarkTags.each {
                    bookmarks << jdoTemplate.getObjectById(Bookmark, it.bookmarkKey)
                }
            }
            finally {
                JdoUtil.closeAll(query)
            }
        } as TransactionCallback)

        new Page(offset, max, total, bookmarks)
    }

    def findAllByUserAndHasTag() {
        def bookmarks = [] as HashSet

        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            def userBookmarkTags = jdoTemplate.find(UserBookmarkTag, "user == userParam", "com.favalike.User userParam", [user] as Object[])

            userBookmarkTags.each {
                def bookmark = jdoTemplate.getObjectById(Bookmark, it.bookmarkKey)

                if(!StringUtils.isBlank(bookmark.commaSeparatedTags)) {
                    bookmarks << jdoTemplate.getObjectById(Bookmark, it.bookmarkKey)
                }
            }
        } as TransactionCallback)

        bookmarks
    }

    def findAllByUserAndHasTagInRange(offset, max) {
        def bookmarks = []
        def total

        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            Query query

            try {
                final filter = "userKey == userKeyParam && commaSeparatedTags != null"
                final parameters = "com.google.appengine.api.datastore.Key userKeyParam"
                query = jdoTemplate.getPersistenceManagerFactory().getPersistenceManager().newQuery(Bookmark)
                query.setFilter(filter)
                query.declareParameters(parameters);
                query.setResult("count(this)")
                total = query.execute(user.key)
                query = jdoTemplate.getPersistenceManagerFactory().getPersistenceManager().newQuery(Bookmark)
                query.setFilter(filter)
                query.declareParameters(parameters);
                query.setOrdering("commaSeparatedTags asc")
                query.setRange(offset, offset + max)
                bookmarks = query.execute(user.key)
            }
            finally {
                JdoUtil.closeAll(query)
            }
        } as TransactionCallback)

        new Page(offset, max, total, bookmarks)
    }

    def findAllByUserAndFavorite() {
        def bookmarks = []

        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            bookmarks = jdoTemplate.find(Bookmark, "userKey == userKeyParam && favorite == favoriteParam", "com.google.appengine.api.datastore.Key userKeyParam, java.lang.Boolean favoriteParam", [user.key, true] as Object[], "created asc")
        } as TransactionCallback)

        bookmarks
    }

    def findAllByUserAndFavoriteInRange(offset, max) {
        def bookmarks = []
        def total

        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            Query query

            try {
                def filter = "userKey == userKeyParam && favorite == true"
                def parameters = "com.google.appengine.api.datastore.Key userKeyParam"
                query = jdoTemplate.getPersistenceManagerFactory().getPersistenceManager().newQuery(Bookmark)
                query.setFilter(filter)
                query.declareParameters(parameters);
                query.setResult("count(this)")
                total = query.execute(user.key)
                query = jdoTemplate.getPersistenceManagerFactory().getPersistenceManager().newQuery(Bookmark)
                query.setFilter(filter)
                query.declareParameters(parameters);
                query.setOrdering("created asc")
                query.setRange(offset, offset + max)
                bookmarks = query.execute(user.key)
            }
            finally {
                JdoUtil.closeAll(query)
            }
        } as TransactionCallback)

        new Page(offset, max, total, bookmarks)
    }

    def findById(bookmarkId) {
        def user
        def bookmark

        transactionTemplate.execute( { status ->
            user = getLoggedInUser()
            Key key = KeyFactory.stringToKey(bookmarkId)
            bookmark = jdoTemplate.getObjectById(Bookmark, key)
        } as TransactionCallback)

        if(user.key != bookmark.userKey) {
            throw new AuthorizationException("Unauthorized access to bookmark with ID '${bookmarkId}'");
        }

        bookmark
    }
	
	def save(bookmark, folderId) {
        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            bookmark.created = new Date()
            bookmark.userKey = user.key
            def folder

            if(!StringUtils.isBlank(folderId)) {
                Key key = KeyFactory.stringToKey(folderId)
                def folders = jdoTemplate.find(Folder, "user == userParam && key == keyParam", "com.favalike.User userParam, com.google.appengine.api.datastore.Key keyParam", [user, key] as Object[])
                folder = JdoUtil.getFirstElement(folders)
            }
            else {
                folder = user.folder
            }

            folder.bookmarks << bookmark
            jdoTemplate.makePersistent(folder)

            assignTagsToBookmark(user, folder, bookmark)
        } as TransactionCallback)
	}
	
	def splitTags(tags) {
		tags.split(',')
	}
	
	def concatinateTags(tags) {
		tags.name.join(', ')
	}

    def assignTagsToBookmark(user, folder, bookmark) {
        if(!StringUtils.isBlank(bookmark.commaSeparatedTags)) {
            splitTags(bookmark.commaSeparatedTags).each {
                def tagName = it.trim()

                if(!StringUtils.isBlank(tagName)) {
                    Tag tag = new Tag(name: it.trim())
                    tagService.save(tag)

                    UserBookmarkTag userBookmarkTag = new UserBookmarkTag(user: user, folderKey: folder.key, bookmarkKey: bookmark.key, tagKey: tag.key, creationDate: new Date())
                    user.userBookmarkTags << userBookmarkTag
                }
            }

            jdoTemplate.makePersistent(user)
        }
    }

	def delete(bookmarkId) {
        transactionTemplate.execute( { status ->
            def bookmark = findById(bookmarkId)
            userBookmarkTagService.deleteForBookmarkKey(bookmark.key)
            jdoTemplate.deletePersistent(bookmark)
        } as TransactionCallback)
	}

    def updateBookmark(bookmarkId, name, location, commaSeparatedTags, description) {
        def bookmark

        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            bookmark = findById(bookmarkId)
            bookmark.name = name
            bookmark.location = new Link(location)
            bookmark.commaSeparatedTags = !StringUtils.isBlank(commaSeparatedTags) ? commaSeparatedTags : null
            bookmark.description = new Text(description)
            jdoTemplate.makePersistent(bookmark)

            userBookmarkTagService.deleteForBookmarkKey(bookmark.key)
            assignTagsToBookmark(user, bookmark.folder, bookmark)
        } as TransactionCallback)

        bookmark
    }

    def updateBookmarkFavorite(bookmarkId, favorite) {
        if(log.debugEnabled) {
            log.debug "Setting favorite flag of bookmark with ID to ${favorite}"
        }

        transactionTemplate.execute( { status ->
            def bookmark = findById(bookmarkId)
            bookmark.favorite = favorite
            jdoTemplate.makePersistent(bookmark)
        } as TransactionCallback)
    }

    def retrieveBookmarkData(url) {
        siteMetaDataBuilder.build(url)
    }
}