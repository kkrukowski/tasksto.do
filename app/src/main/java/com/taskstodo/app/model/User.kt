package com.taskstodo.app.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class User : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var login: String = ""
    var password: String = ""
    var tasks: RealmList<ObjectId> = realmListOf()
}