package com.favalike

import com.google.appengine.api.datastore.KeyFactory
import grails.converters.JSON

class TagController {
	def tagService
    def userBookmarkTagService

    def findAllByUser = {
        log.info "Finding all tags by user"
        def tags = userBookmarkTagService.getTagsByUser()
        renderTagsJson(tags)
    }

    def renderTagsJson(tags) {
        def tagNames = [] as Set
        def tagsJson = []

        tags.each { tag ->
            // Make sure tags are unique
            if(!tagNames.contains(tag.name)) {
                tagsJson << [attributes: [id: "tag_${KeyFactory.keyToString(tag.key)}", rel: "tag"], data: tag.name]
                tagNames << tag.name
            }
        }

        render tagsJson as JSON
    }
}