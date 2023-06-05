package com.taskstodo.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    private val tasks = listOf(
        TaskData("Zadanie 1", "01/06", true),
        TaskData("Zadanie 2", "04/06", false),
        TaskData("Zadanie 3", "12/06", false)
    )

    private lateinit var openTasks: MutableList<TaskData>
    private lateinit var closedTasks: MutableList<TaskData>
    private lateinit var openListView: ListView
    private lateinit var closedListView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userBtn = findViewById<ImageButton>(R.id.userBtn_bottomMenu)
        userBtn.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        val addTaskBtn = findViewById<FloatingActionButton>(R.id.addBtn_bottomMenu)
        addTaskBtn.setOnClickListener{
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

//        Tasks
        openTasks = mutableListOf()
        closedTasks = mutableListOf()
        openListView = findViewById(R.id.tasks_to_do_list)
        closedListView = findViewById(R.id.ended_tasks_to_do_list)

        filterTasks()

//        Opened
        val openAdapter = object : ArrayAdapter<TaskData>(this, 0, openTasks) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView

                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.task_to_do_list_item, parent, false)
                }

                val task = tasks[position]

                val taskName = view?.findViewById<TextView>(R.id.task_name)
                val taskDate = view?.findViewById<TextView>(R.id.task_date)
                val taskButton = view?.findViewById<Button>(R.id.task_button)

                taskName?.text = task.name
                taskDate?.text = task.date

                taskButton?.setOnClickListener {
                    task.isClosed = !task.isClosed
                    notifyDataSetChanged()
                }

                return view!!
            }
        }

        openListView.adapter = openAdapter

//        Closed
        val closedAdapter = object : ArrayAdapter<TaskData>(this, 0, closedTasks) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView

                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.task_to_do_list_ended_item, parent, false)
                }

                val task = tasks[position]

                val taskName = view?.findViewById<TextView>(R.id.task_name)
                val taskDate = view?.findViewById<TextView>(R.id.task_date)
                val taskButton = view?.findViewById<Button>(R.id.task_button)

                taskName?.text = task.name
                taskDate?.text = task.date

                taskButton?.setOnClickListener {
                    task.isClosed = !task.isClosed
                    notifyDataSetChanged()
                }

                return view!!
            }
        }

        closedListView.adapter = closedAdapter
    }
    private fun filterTasks() {
        openTasks.clear()
        closedTasks.clear()

        for (task in tasks) {
            if (task.isClosed) {
                closedTasks.add(task)
            } else {
                openTasks.add(task)
            }
        }
    }
}