package com.favalike

import com.google.appengine.api.datastore.Key
import javax.jdo.annotations.*

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
class Tag {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key key
	
	@Persistent
	String name

    static mapping = {
        id visible: false
        version visible: false
    }

	static constraints = {
		key nullable: true
		name blank: false
	}
	
	@Override
    String toString() {
        "Tag{key='${key}', name='${name}'}"
    }
}