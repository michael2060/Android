package com.example.myapplication.Hw

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_hw_edit.*
import java.text.DateFormat
import java.util.*

class HwEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hw_edit)
        val uid = intent.getIntExtra("uid", 0)
        val msg = intent.getStringExtra("msg")
        val position = intent.getIntExtra("position", 0)
        etxtmsg.setText(msg)
        btnmsgupd.setOnClickListener {
            setResult(Activity.RESULT_OK)
            val date = Date()
            //フォーマット指定
            val dateformat: DateFormat = DateFormat.getDateTimeInstance()
            val now = dateformat.format(date)
            intent.apply {
                putExtra("uid", uid)
                putExtra("position", position)
                putExtra("time", now)
                putExtra("msg", etxtmsg.text.toString())
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}