package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_constraint_.*
import kotlinx.android.synthetic.main.item_homework_sendinfo.view.*
import java.text.DateFormat
import java.util.*

class Constraint_Activity : AppCompatActivity() {
    private lateinit var adapter: SendInfoAdapter
    private lateinit var Spinnerview: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_)
        adapter = SendInfoAdapter(this)
        ListViewSendInfo.adapter = adapter
        btnSend.setOnClickListener {
            val date = Date()
            val dateformat: DateFormat = DateFormat.getDateTimeInstance()
            val now = dateformat.format(date)
            Sendbtnclick("シュウ", now, editTextMessage.text.toString())
        }

    }

    fun Sendbtnclick(name: String, datetime: String, message: String) {
        val info = Addsendinfo(name, datetime, message)
        adapter.add(info)
        editTextMessage.text = null

    }



    private class SendInfoAdapter(context: Context) : ArrayAdapter<Addsendinfo>(context, R.layout.item_homework_sendinfo) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView?: LayoutInflater.from(context).inflate(R.layout.item_homework_sendinfo, parent, false)

            val item = getItem(position)

            view.Sendmsg.text = item?.SendMessage
            view.SendName.text = item?.name
            view.SendTimeDate.text = item?.dateTime
            val spinneritem = arrayOf("既読","未読")
            view.sendspinner.adapter = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,spinneritem)
            view.sendspinner.setOnItemClickListener {
                parent, view, position, id ->

            }

            return view
        }


    }
}