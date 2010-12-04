package com.favalike

import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.KeyFactory
import org.springframework.transaction.support.TransactionCallback

class TagService extends BaseService {
	def jdoTemplate
    def userBookmarkTagService

    def findByTagId(tagId) {
        def tag

        transactionTemplate.execute( { status ->
            Key key = KeyFactory.stringToKey(tagId)
            tag = jdoTemplate.getObjectById(Tag, key)
        } as TransactionCallback)

        tag
    }

	def save(Tag tag) {
		def existingTags = jdoTemplate.find(Tag, "name == nameParam", "String nameParam", [tag.name] as Object[])

        // Only persist if the tag doesn't exist yet
		if(existingTags.size() == 0) {
            jdoTemplate.makePersistent(tag)
		}
		else {
			tag.key = existingTags.get(0).key
		}
	}
}