package com.favalike

import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.Link
import com.google.appengine.api.datastore.Text
import javax.jdo.annotations.*

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
class Bookmark {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key key

	@Persistent
	String name

	@Persistent
	Link location
	
	@Persistent
	Text description

    @Persistent
	String commaSeparatedTags

    @Persistent
    Link faviconUrl

    @Persistent
    Date created

    @Persistent
    boolean favorite

    @Persistent
    Folder folder

    @Persistent
    Key userKey

    static mapping = {
        id visible: false
        version visible: false
    }

	static constraints = {
		key nullable: true
		name blank: false
		location blank: false
		description blank: true
        commaSeparatedTags nullable: true
        created nullable: false
        faviconUrl nullable: true
        favorite nullable: false 
        folder nullable: false
        userKey nullable: false
	}

	@Override
    String toString() {
        "Bookmark{key='${key}', name='${name}', location='${location}', description='${description}', commaSeparatedTags='${commaSeparatedTags}', faviconUrl='${faviconUrl}', created='${created}', favorite='${favorite}'}"
    }
}