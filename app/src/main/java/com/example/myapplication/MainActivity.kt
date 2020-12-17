package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("onCreate")
        val Homework: Button = findViewById(R.id.btnHomework)
        val linearLayout: Button = findViewById(R.id.btnLinearLayout)
        Homework.setOnClickListener {
            Timber.d("onclick")
            startActivity(Intent(this, Constraint_Activity::class.java))
        }
        linearLayout.setOnClickListener {
            Timber.d("onclick2")
            startActivity(Intent(this, LinearActiviy::class.java))
        }
    }
}