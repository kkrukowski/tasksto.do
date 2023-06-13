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
import com.taskstodo.app.database.Realm
import com.taskstodo.app.model.Task
import com.taskstodo.app.model.User.Companion.globalUser
import io.realm.kotlin.ext.query
import android.util.Log
import androidx.lifecycle.lifecycleScope
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class HomeActivity : AppCompatActivity() {

    val tasksOfUser = globalUser!!.tasks

    private val tasks = mutableListOf<TaskData>()

    private lateinit var openTasks: MutableList<TaskData>
    private lateinit var closedTasks: MutableList<TaskData>
    private lateinit var openListView: ListView
    private lateinit var closedListView: ListView

    suspend fun closeTaskDb(task: TaskData){
        val realm = Realm.getRealmInstance()
        realm.writeBlocking {
            val dbTask: Task? = this.query<Task>("_id == $0", task._id).first().find()
            dbTask?.isClosed = true
        }
        print("Task ended")
        realm.close()
    }

    suspend fun openTaskDb(task: TaskData){
        val realm = Realm.getRealmInstance()
        realm.write {
            val dbTask = realm.query<Task>("_id == $0", task._id).first().find()
            dbTask?.isClosed = false
        }
        realm.close()
    }
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
        val realm = Realm.getRealmInstance()
        for(taskId in tasksOfUser){
            val taskFromUser = realm.query<Task>("_id==$0", taskId).first().find()
            println(taskFromUser!!.name + " " + taskFromUser.isClosed)

            tasks.add(TaskData(taskFromUser._id, taskFromUser.name, taskFromUser.date, taskFromUser.isClosed))
        }
        realm.close()
        showAllTasks()
        filterTasks()

//        Opened
        val openAdapter = object : ArrayAdapter<TaskData>(this, 0, openTasks) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView

                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.task_to_do_list_item, parent, false)
                }
                val task = openTasks[position]

                val taskName = view?.findViewById<TextView>(R.id.task_name)
                val taskDate = view?.findViewById<TextView>(R.id.task_date)
                val taskButton = view?.findViewById<Button>(R.id.task_button)

                taskName?.text = task.name
                taskDate?.text = task.date

                taskButton?.setOnClickListener {
                    val taskIndex = tasks.indexOfFirst { it._id == task._id }
                    lifecycleScope.launch {
                        closeTaskDb(tasks[taskIndex])
                    }
                    tasks[taskIndex].isClosed = true
                    filterTasks()
                    showOpenTasks()
                    showClosedTasks()
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

                val task = closedTasks[position]

                val taskName = view?.findViewById<TextView>(R.id.task_name)
                val taskDate = view?.findViewById<TextView>(R.id.task_date)
                val taskButton = view?.findViewById<Button>(R.id.task_button)

                taskName?.text = task.name
                taskDate?.text = task.date

                taskButton?.setOnClickListener {
                    val taskIndex = tasks.indexOfFirst { it._id == task._id }
                    tasks[taskIndex].isClosed = false
                    val dbTask = realm.query<Task>("_id == $0", task._id).first().find()
//                    dbTask?.isClosed = false
                    Log.d("TAG", "Przycisk został kliknięty")
                    filterTasks()
                    showClosedTasks()
                    showOpenTasks()
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

    private fun showAllTasks(){
        println("All tasks")
        for (task in tasks) {
            println(task.name + " " + task.isClosed)
        }
    }

    private fun showClosedTasks(){
        println("Closed tasks")
        for (task in tasks) {
            if (task.isClosed) {
                println(task.name)
            }
        }
    }

    private fun showOpenTasks(){
        println("Open tasks")
        for (task in tasks) {
            if (!task.isClosed) {
                println(task.name)
            }
        }
    }
}