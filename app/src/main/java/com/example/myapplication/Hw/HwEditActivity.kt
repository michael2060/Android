package com.example.myapplication.Hw

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_hw_edit.btnmsgupd
import kotlinx.android.synthetic.main.activity_hw_edit.etxtmsg
import java.text.DateFormat
import java.util.Date

class HwEditActivity : AppCompatActivity() {
    companion object {
        const val KEY_SENDINFO = "send_info"
    }

    private var sendInfo: Addsendinfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hw_edit)

        sendInfo = intent.getParcelableExtra(KEY_SENDINFO)
        sendInfo?.also {
            etxtmsg.setText(it.sendMessage)
        }

        btnmsgupd.setOnClickListener {
            sendInfo?.also {
                //フォーマット指定
                val dateformat: DateFormat = DateFormat.getDateTimeInstance()
                val date = Date()
                val now = dateformat.format(date)
                it.sendTime = now
                it.sendMessage = etxtmsg.text.toString()
                val resultIntent = Intent().apply {
                    putExtra(KEY_SENDINFO, it)
                }
                setResult(Activity.RESULT_OK, resultIntent)
            }
            finish()
        }
    }
}
