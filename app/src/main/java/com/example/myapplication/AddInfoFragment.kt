package com.example.myapplication

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.item_homework_sendinfo.*
import timber.log.Timber
import timber.log.Timber.d

class AddInfoFragment: Fragment() {
    private lateinit var spinnerAdapter:SpinnerAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_homework_sendinfo,container,false)
    }

    override fun onStart() {
        super.onStart()
        val spinneritem = arrayOf("既読","未読")
        spinnerAdapter = ArrayAdapter<String>(this.context,android.R.layout.simple_spinner_item,spinneritem)

        sendspinner.setOnItemClickListener { parent, view, position, id ->
            Timber.d("${position}")
        }

    }

}