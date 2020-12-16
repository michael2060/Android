package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_linear_activiy.*
import kotlinx.android.synthetic.main.item_linearlayout.view.*

class LinearActiviy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_activiy)
        linearInflater.setOnClickListener {
            val inflater = LayoutInflater.from(this)
            val view = inflater.inflate(R.layout.item_linearlayout, linearlayout, false)
            view.nameid.text = "シュウ"
            linearlayout.addView(view)
            
        }
    }
}