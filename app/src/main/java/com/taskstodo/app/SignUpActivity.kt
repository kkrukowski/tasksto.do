package com.taskstodo.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.taskstodo.app.database.Realm
import com.taskstodo.app.model.User
import io.realm.kotlin.ext.query

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val realm = Realm.getRealmInstance()
        val btn_login_sign_up = findViewById<Button>(R.id.btn_login_sign_up)
        val signUpBtn = findViewById<Button>(R.id.loginBtn_login)
        val loginInput = findViewById<EditText>(R.id.loginInput_register)
        val passwordInput = findViewById<EditText>(R.id.passwordInput_register)
        val rePasswordInput = findViewById<EditText>(R.id.rePasswordInput_register)

        signUpBtn.setOnClickListener{
            if(loginInput.text.toString().isNotEmpty() and rePasswordInput.text.toString().isNotEmpty() and passwordInput.text.toString().isNotEmpty()){
                if(rePasswordInput.text.toString() == passwordInput.text.toString()){
                    val foundUser: User? = realm.query<User>("login=='${loginInput.text.toString()}' AND password=='${passwordInput.text.toString()}'").first().find()
                    if(foundUser == null){
                        realm.writeBlocking {
                            copyToRealm(User().apply {
                                login = loginInput.text.toString()
                                password = passwordInput.text.toString()
                            })
                        }
                        println("ZAREJESTROWANO!")
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        println("ZNALEZIONO TAKIEGO UZYTKOWNIKA!")
                        val intent = Intent(this, SignUpActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    println("HASLA SIE NIE ZGADZAJA!")
                    val intent = Intent(this, SignUpActivity::class.java)
                    startActivity(intent)
                }
            } else {
                println("NIEPOPRAWNE DANE!")
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }

        btn_login_sign_up.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}