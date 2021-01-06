package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
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
        btnSpinner.setOnClickListener {
            startActivity(Intent(this, SpinnerActivity::class.java))
        }
        btnIntent.setOnClickListener {
            startActivity(Intent(this, IntentActivity::class.java))
        }
        btnhwintent.setOnClickListener {
            startActivity(Intent(this, HwIntentActivity::class.java))
        }
        btnRoom.setOnClickListener {
            startActivity(Intent(this, RoomActivity::class.java))
        }

    }
}