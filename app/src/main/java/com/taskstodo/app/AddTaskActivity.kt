package com.taskstodo.app

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.taskstodo.app.database.Realm
import com.taskstodo.app.model.Task
import com.taskstodo.app.model.User
import com.taskstodo.app.model.User.Companion.globalUser
import io.realm.kotlin.ext.query
import io.realm.kotlin.internal.objectIdToRealmObjectId
import kotlinx.coroutines.CoroutineStart
import android.widget.TextView
import org.mongodb.kbson.ObjectId
import java.util.Calendar

class AddTaskActivity : AppCompatActivity() {
    private lateinit var dateBtn: Button
    private lateinit var dateText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        val addTaskBtn = findViewById<Button>(R.id.addTask_Button)
        val backButton = findViewById<ImageButton>(R.id.backBtn)
        val nameTaskInput = findViewById<EditText>(R.id.taskNameInput)
        val realm = Realm.getRealmInstance()
        var dateTextData = ""
        val userId = globalUser?._id


        backButton.setOnClickListener{
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }
        addTaskBtn.setOnClickListener{
            realm.writeBlocking {
                val user: User? = this.query<User>("_id==$0", globalUser?._id).first().find()
                val task = copyToRealm(Task().apply {
                    name = nameTaskInput.text.toString()
                    date = dateTextData.toString()
                })
                user?.tasks?.add(task._id)
            }
            globalUser = null
            val user: User? = realm.query<User>("_id==$0", userId).first().find()
            globalUser = user

            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        dateBtn = findViewById(R.id.add_task_date_btn)
        dateText = findViewById(R.id.add_task_date)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dateBtn.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, myear, mmonth, mday ->
                dateText.text = "$mday/${mmonth-1}/$myear"
                dateTextData = "$mday/${mmonth-1}/$myear"
            }, year, month, day).show()
        }
    }
}