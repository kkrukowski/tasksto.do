package com.taskstodo.app.database
import com.taskstodo.app.model.Task
import com.taskstodo.app.model.User
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

object Realm {
    private lateinit var realm: Realm

    fun initializeRealm() {
        val config = RealmConfiguration.create(schema = setOf(User::class, Task::class))
        realm = Realm.open(config)
    }

    fun getRealmInstance(): Realm {
        if (!::realm.isInitialized) {
            throw IllegalStateException("Realm has not been initialized. Call initializeRealm() first.")
        }
        return realm
    }
}