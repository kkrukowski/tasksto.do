package com.taskstodo.app

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import java.util.Calendar

class AddTaskActivity : AppCompatActivity() {
    private lateinit var dateBtn: Button
    private lateinit var dateText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val backButton = findViewById<ImageButton>(R.id.backBtn)
        backButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
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
            }, year, month, day).show()
        }
    }
}