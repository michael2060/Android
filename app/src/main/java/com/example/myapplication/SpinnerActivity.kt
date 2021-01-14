package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Hw.Addinfo
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_spinner.*
import timber.log.Timber

class SpinnerActivity : AppCompatActivity() {
    private lateinit var messageAdapter: ArrayAdapter<Addinfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinner)
        val list = arrayOf(
            Addinfo("メッセージ1"),
            Addinfo("メッセージ2")
        )
        messageAdapter =
            object : ArrayAdapter<Addinfo>(this, android.R.layout.simple_spinner_item, list) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val sample = getItem(position)

                    val view = convertView
                        ?: LayoutInflater.from(context)
                            .inflate(android.R.layout.simple_spinner_item, parent, false)
                    val text1 = view.findViewById<TextView>(android.R.id.text1)

                    text1.text = sample?.name

                    return view
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val sample = getItem(position)

                    val view = convertView
                        ?: LayoutInflater.from(context)
                            .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
                    val text1 = view.findViewById<TextView>(android.R.id.text1)

                    text1.text = sample?.name

                    return view
                }
            }
        spinner.adapter = messageAdapter

        btnspinnerclick.setOnClickListener {
            Sendspinnermsg()
            val snackbar = Snackbar.make(btnspinnerclick, "クリックされました", Snackbar.LENGTH_SHORT)
            snackbar.setAction("OK") {
                Timber.d("OK")
            }.show()
        }
    }

    fun Sendspinnermsg() {
        val select = spinner.selectedItem as Addinfo
        Timber.d("${select.name}")
    }


}