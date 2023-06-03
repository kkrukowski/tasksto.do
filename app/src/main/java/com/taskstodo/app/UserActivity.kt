package com.taskstodo.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.taskstodo.app.model.User.Companion.globalUser

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val logOutBtn = findViewById<Button>(R.id.addTask_Button)
        val addTaskBtn = findViewById<FloatingActionButton>(R.id.addBtn_bottomMenu)
        val userName = findViewById<TextView>(R.id.textView3)
        val tasksBtn = findViewById<ImageButton>(R.id.tasksBtn_bottomMenu)

        userName.text = "Witaj ${globalUser!!.login}!"
        println(globalUser!!.tasks)

        tasksBtn.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        logOutBtn.setOnClickListener{
            globalUser = null
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        addTaskBtn.setOnClickListener{
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }
}