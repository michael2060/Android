package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("onCreate")
        val button1: Button = findViewById(R.id.button)
        button1.setOnClickListener {
            Timber.d("onclick")
            startActivity(Intent(this, LinearActiviy::class.java))
        }
    }
}