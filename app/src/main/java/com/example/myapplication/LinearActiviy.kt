package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_linear_activiy.*
import kotlinx.android.synthetic.main.item_linearlayout.view.*

class LinearActiviy : AppCompatActivity() {

    private lateinit var adapter: PersonalInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_activiy)
        adapter = PersonalInfoAdapter(this)
        listview.adapter = adapter
        linearInflater.setOnClickListener {
            addpersoninfo2()
        }
    }

    fun addpersoninfo1() {
        // val inflater = LayoutInflater.from(this)
        // val view = inflater.inflate(R.layout.item_linearlayout, linearlayout, false)
        //view.nameid.text = "シュウ"
        // linearlayout.addView(view)
    }

    fun addpersoninfo2() {
        val info = Addinfo("${adapter.count}")
        adapter.add(info)

    }


    private class PersonalInfoAdapter(context: Context) :
            ArrayAdapter<Addinfo>(context, R.layout.item_linearlayout) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val view =
                    convertView ?: LayoutInflater.from(context)
                            .inflate(R.layout.item_linearlayout, parent, false)

            val item = getItem(position)
            view.nameid.text = item?.name
            return view
        }
    }
}