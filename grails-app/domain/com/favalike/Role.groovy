package com.favalike

import com.google.appengine.api.datastore.Key
import javax.jdo.annotations.*

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
class Role {
    @PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key key

    @Persistent
    String authority

    @Persistent
    User user

    static mapping = {
        id visible: false
        version visible: false
    }

    static constraints = {
        key nullable: true
        authority blank: false
    }

    @Override
    String toString() {
        "Role{key='${key}', authority='${authority}'}"
    }
}
