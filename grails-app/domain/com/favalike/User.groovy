package com.favalike

import com.google.appengine.api.datastore.Key
import javax.jdo.annotations.*

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
class User {
    @PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key key

    @Persistent
    String firstname

    @Persistent
    String lastname

    @Persistent
    String username

    @Persistent
    String password

    @Persistent
    boolean enabled

    @Persistent
    boolean confirmed

    @Persistent
    String token

    @Persistent
    Date created

    @Persistent(defaultFetchGroup = "true")
    List<Role> authorities  = new ArrayList<Role>()

    @Persistent(mappedBy = "user")
    List<UserBookmarkTag> userBookmarkTags = new ArrayList<UserBookmarkTag>()

    @Persistent
    Folder folder

    static mapping = {
        id visible: false
        version visible: false
    }

    static constraints = {
        key nullable: true
        firstname blank: false
        lastname blank: false
        username email: true, blank: false
        password blank: false
        folder nullable: true
        token nullable: true
        created nullable: true
    }

    @Override
    String toString() {
        "User{key='${key}', firstname='${firstname}', lastname='${lastname}', username='${username}', password='${password}', enabled='${enabled}', confirmed='${confirmed}', token='${token}', created='${created}'}"
    }
}
