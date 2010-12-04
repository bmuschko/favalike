package com.favalike

import com.google.appengine.api.datastore.Key
import javax.jdo.annotations.*

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
class UserBookmarkTag {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key key

    @Persistent
	User user

    @Persistent
    Key folderKey

	@Persistent
	Key bookmarkKey
	
	@Persistent
	Key tagKey
	
	@Persistent
	Date creationDate

    static mapping = {
        id visible: false
        version visible: false
    }

	static constraints = {
		key nullable: true
		bookmarkKey nullable: false
		tagKey nullable: false
		creationDate nullable: false
	}
	
	@Override
    String toString() {
        "UserBookmarkTag{key='${key}', folderKey='${folderKey}', bookmarkKey='${bookmarkKey}', tagKey='${tagKey}', creationDate='${creationDate}'}"
    }
}