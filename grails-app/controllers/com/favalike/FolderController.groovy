package com.favalike

import com.google.appengine.api.datastore.KeyFactory
import grails.converters.JSON

class FolderController {
	def folderService

    def findAllByFolderId = {
        log.info "Finding all folders by folder ID"
        def subFolders = folderService.findAllByFolderId(params.folderId)
        renderFoldersJson(subFolders)
    }

    def findAllByParentFolder = {
        log.info "Finding all folders by parent folder"
        def subFolders = folderService.findAllByParentFolder()
        renderFoldersJson(subFolders)
    }

    def findByFolderId = {
        log.info "Finding folder by folder ID"
        def folder = folderService.findById(params.folderId)
        renderFolderJson(folder)
    }
	
	def add = {
		log.info "Adding folder"
		def newFolder = new Folder(name: params.name, description: params.description)
		folderService.addFolderToFolder(params.parentFolderId, newFolder)
		renderFolderJson(newFolder)
	}
	
	def delete = {
		log.info "Deleting folder" 
		
		folderService.delete(params.id)

		response.setStatus(200) 
		render "success"
	}

    def update = {
        log.info "Updating folder"
		def folder = folderService.updateFolder(params.id, params.name, params.description)
		renderFolderJson(folder)
    }

    def renderFolderJson(folderData) {
        render(contentType:'text/json') {
            folder(id: KeyFactory.keyToString(folderData.key), name: folderData.name, description: folderData.description?.value, parentId: (folderData.parentFolderKey != null) ? KeyFactory.keyToString(folderData.parentFolderKey) : null)
		}
    }

    def renderFoldersJson(subFolders) {
        def subFoldersJson = []

        subFolders.each { subFolder ->
            subFoldersJson << [attributes: [id: "folder_${KeyFactory.keyToString(subFolder.key)}", rel: "folder"], data: subFolder.name, state : "closed"]
        }

        render subFoldersJson as JSON
    }
}