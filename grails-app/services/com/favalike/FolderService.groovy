package com.favalike

import com.favalike.security.AuthorizationException
import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Text
import org.springframework.transaction.support.TransactionCallback

class FolderService extends BaseService {
    def bookmarkService
    def userBookmarkTagService

    def findAllByFolderId(folderId) {
        def folders = []

        transactionTemplate.execute( { status ->
            Folder folder = findById(folderId)

            folder.folderKeys.each { folderKey ->
                folders <<  jdoTemplate.getObjectById(Folder, folderKey)
            }
        } as TransactionCallback)

       folders
    }

    def findAllByParentFolder() {
        def folders = []

        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            user.folder.folderKeys.each { folderKey ->
                folders <<  jdoTemplate.getObjectById(Folder, folderKey)
            }
        } as TransactionCallback)

        folders
    }

    def findAllByFolder(folder) {
        def folders = []

        transactionTemplate.execute( { status ->
            folder.folderKeys.each { folderKey ->
                folders <<  jdoTemplate.getObjectById(Folder, folderKey)
            }
        } as TransactionCallback)

        folders
    }

    def findById(folderId) {
        def user
        def folder

        transactionTemplate.execute( { status ->
            user = getLoggedInUser()
            Key key = KeyFactory.stringToKey(folderId)
            folder = jdoTemplate.getObjectById(Folder, key)
        } as TransactionCallback)

        if(user != folder.user) {
            throw new AuthorizationException("Unauthorized access to folder with ID '${folderId}'");
        }

        folder
    }
	
	def addFolderToFolder(parentFolderId, folder) {
        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()

            // Get parent folder
            Folder parentFolder = findById(parentFolderId)

            // Save new folder
            folder.user = user
            folder.parentFolderKey = parentFolder.key
            jdoTemplate.makePersistent(folder)

            // Add new folder to parent folder
            parentFolder.folderKeys << folder.key
            jdoTemplate.makePersistent(parentFolder)
        } as TransactionCallback)
	}
	
	def addBookmarkToFolder(folderId, bookmark) {
		Folder folder = findById(folderId)
		folder.bookmarks << bookmark
		jdoTemplate.makePersistent(folder)
	}
	
	def delete(folderId) {
        transactionTemplate.execute( { status ->
            def user = getLoggedInUser()
            Folder folder = findById(folderId)

            // Delete folder key from parent folder
            Folder parentFolder = findById(KeyFactory.keyToString(folder.parentFolderKey))
            parentFolder.folderKeys.remove(folder.key)
            jdoTemplate.makePersistent(parentFolder)

            // Delete children
            cascadeDeleteFolder(user, folder)
        } as TransactionCallback)
	}

    def cascadeDeleteFolder(user, folder) {
        folder.folderKeys.each { folderKey ->
            def subFolder = findById(KeyFactory.keyToString(folderKey))
            cascadeDeleteFolder(user, subFolder)
        }

        def userBookmarkTags = userBookmarkTagService.getUserBookmarkTagsByFolderKey(folder.key)

        userBookmarkTags.each { userBookmarkTag ->
            jdoTemplate.deletePersistent(userBookmarkTag)
        }

        jdoTemplate.deletePersistent(folder)
    }

    def updateFolder(id, name, description) {
        def folder

        transactionTemplate.execute( { status ->
            folder = findById(id)
            folder.name = name
            folder.description = new Text(description)
            jdoTemplate.makePersistent(folder)
        } as TransactionCallback)

        folder
    }
}