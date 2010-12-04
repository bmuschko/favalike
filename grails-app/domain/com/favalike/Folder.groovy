package com.favalike

import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.Text
import javax.jdo.annotations.*

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
class Folder {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key key
	
	@Persistent
	String name
	
	@Persistent
	Text description

	@Persistent(mappedBy = "folder")
    @Element(dependent = "true")
	List<Bookmark> bookmarks = new ArrayList<Bookmark>()

    @Persistent
    List<Key> folderKeys = new ArrayList<Key>()

    @Persistent(mappedBy = "folder")
    User user

    @Persistent
    Key parentFolderKey

    static mapping = {
        id visible: false
        version visible: false
    }

	static constraints = {
		key nullable: true
		name blank: false
		description blank: true
	}
	
	@Override
    String toString() {
        "Folder{key='${key}', name='${name}', description='${description}', parentFolderKey='${parentFolderKey}'}"
    }
}