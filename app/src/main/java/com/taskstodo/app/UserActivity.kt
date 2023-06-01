package com.taskstodo.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val tasksBtn = findViewById<ImageButton>(R.id.tasksBtn_bottomMenu)
        tasksBtn.setOnClickListener{
            val intent = Intent(this, tasksBtn::class.java)
            startActivity(intent)
        }
    }
}