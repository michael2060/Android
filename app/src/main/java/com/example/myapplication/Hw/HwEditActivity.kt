package com.example.myapplication.Hw

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_hw_edit.*
import java.text.DateFormat
import java.util.*

class HwEditActivity : AppCompatActivity() {
    public companion object {
        const val ADDSENDINFO = "ADDSENDINFO"
        const val POSITION = "POSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hw_edit)
        val uid = intent.getIntExtra("uid", 0)
        val msg = intent.getStringExtra("msg")
        val position = intent.getIntExtra(POSITION, 0)
        val addsendinfo = intent.getParcelableExtra<Addsendinfo>(ADDSENDINFO)
        etxtmsg.setText(msg)
        btnmsgupd.setOnClickListener {
            setResult(Activity.RESULT_OK)
            val date = Date()
            //フォーマット指定
            val dateformat: DateFormat = DateFormat.getDateTimeInstance()
            val now = dateformat.format(date)

            intent.apply {
                if (addsendinfo != null) {
                    addsendinfo.sendTime = now
                    addsendinfo.sendMessage = etxtmsg.text.toString()

                    putExtra(ADDSENDINFO, addsendinfo)
                    putExtra(POSITION, position)
                }

                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}