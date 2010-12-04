package com.favalike

import org.springframework.transaction.support.TransactionCallback

class UserBookmarkTagService extends BaseService {
    def getUserBookmarkTagsByFolderKey(folderKey) {
		jdoTemplate.find(UserBookmarkTag, "folderKey == folderKeyParam", "com.google.appengine.api.datastore.Key folderKeyParam", [folderKey] as Object[])
	}

	def getUserBookmarkTagsByBookmarkKey(bookmarkKey) {
		jdoTemplate.find(UserBookmarkTag, "bookmarkKey == bookmarkKeyParam", "com.google.appengine.api.datastore.Key bookmarkKeyParam", [bookmarkKey] as Object[])
	}

    def getUserBookmarkTagsByUser(user) {
		jdoTemplate.find(UserBookmarkTag, "user == userParam", "com.favalike.User userParam", [user] as Object[])
	}

    def getUserBookmarkTagsByUserAndTagKey(user, tagKey) {
        jdoTemplate.find(UserBookmarkTag, "user == userParam && tagKey == tagKeyParam", "com.favalike.User userParam, com.google.appengine.api.datastore.Key tagKeyParam", [user, tagKey] as Object[])
    }
	
	def getTagsByBookmarkKey(bookmarkKey) {
		def userBookmarkTags = getUserBookmarkTagsByBookmarkKey(bookmarkKey)
		def tags = []
		
		userBookmarkTags.each {
            tags << jdoTemplate.getObjectById(Tag, it.tagKey)
		}
		
		tags
	}

    def getTagsByUser() {
        def user = getLoggedInUser()
        def userBookmarkTags = getUserBookmarkTagsByUser(user)

        def tags = []

        userBookmarkTags.each {
            tags << jdoTemplate.getObjectById(Tag, it.tagKey)
        }

        tags
    }
	
	def save(userBookmarkTag) {
		jdoTemplate.makePersistent(userBookmarkTag)
	}

    def deleteForBookmarkKey(bookmarkKey) {
        transactionTemplate.execute( { status ->
            def userBookmarkTags = getUserBookmarkTagsByBookmarkKey(bookmarkKey)

            userBookmarkTags.each { userBookmarkKey ->
                jdoTemplate.deletePersistent(userBookmarkKey)
            }
        } as TransactionCallback)
    }
}