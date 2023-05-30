package com.taskstodo.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.taskstodo.app.model.Task
import com.taskstodo.app.model.User
import com.taskstodo.app.model.User.Companion.globalUser
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(globalUser != null){
            println("Użytkownik: ${globalUser!!._id}")
            println("Użytkownik: ${globalUser!!.login}")
            println("Użytkownik: ${globalUser!!.password}")
            println("Użytkownik: ${globalUser!!.tasks}")
        }

        /*val config = RealmConfiguration.create(schema = setOf(User::class, Task::class))
        val realm: Realm = Realm.open(config)
        var users: RealmResults<User> = realm.query<User>().find()
        if(users.size == 0){
            realm.writeBlocking {
                copyToRealm(User().apply {
                    login = "test"
                    password = "12345"
                })
            }
            realm.writeBlocking {
                copyToRealm(User().apply {
                    login = "test2"
                    password = "12345"
                })
            }
        }
        users = realm.query<User>().find()
        for (user in users){
            println("User id: ${user._id}, name: ${user.login}, password: ${user.password}")
        }*/
    }
}