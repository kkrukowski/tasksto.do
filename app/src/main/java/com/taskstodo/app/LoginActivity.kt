package com.taskstodo.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.taskstodo.app.database.Realm
import com.taskstodo.app.model.User
import com.taskstodo.app.model.User.Companion.globalUser
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val logInBtn = findViewById(R.id.loginBtn_login) as Button
        Realm.initializeRealm()
        val btn_sign_up_login = findViewById<Button>(R.id.btn_sign_up_login)
        logInBtn.setOnClickListener(){
            val loginInput = findViewById(R.id.loginInput_register) as EditText
            val passwordInput = findViewById(R.id.passwordInput_register) as EditText

            Realm.initializeRealm()
            val realm = Realm.getRealmInstance()
            val user: User? = realm.query<User>("login=='${loginInput.text.toString()}' AND password=='${passwordInput.text.toString()}'").first().find()

            println("Login: ${loginInput.text.toString()}, Hasło: ${passwordInput.text.toString()}")
            if (user != null) {
                println("ZAREJESTROWANO!");
                globalUser = user
                val intent = Intent(this, MainActivity::class.java) // tutaj na strone główną
                startActivity(intent)
            }
        }
        btn_sign_up_login.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}