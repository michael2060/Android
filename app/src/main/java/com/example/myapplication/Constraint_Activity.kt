package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_constraint_.*
import kotlinx.android.synthetic.main.item_homework_sendinfo.view.*
import kotlinx.coroutines.*
import java.text.DateFormat
import java.util.*

class Constraint_Activity : AppCompatActivity() {
    //

    //listview adapter
    private lateinit var listviewadapter: SendInfoAdapter

    //spinner
    private lateinit var spinnerAdapter: SpinnerAdapter

    //Enumクラス
    private lateinit var spinnerstatus: EnumSendStatus
    private lateinit var sendtool: EnumSendtool

    //
    private val job = Job()
    private val coroutinScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var db: UserDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_)
        listviewadapter = SendInfoAdapter(this)
        ListViewSendInfo.adapter = listviewadapter


        btnSend.setOnClickListener {

            //現在日時を取得
            val date = Date()
            //フォーマット指定
            val dateformat: DateFormat = DateFormat.getDateTimeInstance()
            val now = dateformat.format(date)

            sendtool = if (rbtnSendPC.isChecked) {
                EnumSendtool.PC
            } else if (rbtnSendAndroid.isChecked) {
                EnumSendtool.Android
            } else {
                EnumSendtool.PC
            }

            spinnerstatus = when (spinnersend.selectedItem.toString()) {
                "下書き" -> EnumSendStatus.DRAFT
                else -> EnumSendStatus.OPEN
            }
            //sendボタンクリックイベント
            Sendbtnclick("シュウ", now, editTextMessage.text.toString(), sendtool, spinnerstatus)

        }
        //Spinnerリストを作成
        val spinnerlist = arrayOf("公開", "下書き")

        spinnerAdapter = SpinnerAdapter(this, spinnerlist)

        spinnersend.adapter = spinnerAdapter

        rbtnSendPC.isChecked = true
    }


    fun Sendbtnclick(
        name: String,
        datetime: String,
        message: String,
        sendtool: EnumSendtool,
        sendStatus: EnumSendStatus
    ) {


        val info = Addsendinfo(0, name, datetime, message, sendtool, sendStatus)

        listviewadapter.add(info)

        if (spinnerstatus == EnumSendStatus.OPEN) {
            val snackbar = Snackbar.make(btnSend, "公開しました。", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("OK") {
            }.show()
        }
        editTextMessage.text = null

        coroutinScope.launch {
            withContext(Dispatchers.IO) {
                db.addsendinfoDao().insert(info)
            }
        }

    }


    private class SendInfoAdapter(context: Context) :
        ArrayAdapter<Addsendinfo>(context, R.layout.item_homework_sendinfo) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val view = convertView
                ?: LayoutInflater.from(context)
                    .inflate(R.layout.item_homework_sendinfo, parent, false)

            val item = getItem(position)

            view.Sendmsg.text = item?.sendMessage
            view.SendName.text = item?.name
            view.SendTimeDate.text = item?.sendTime
            view.sendtool.text = when (item?.sendTool) {
                EnumSendtool.PC -> "PCから"
                EnumSendtool.Android -> "Androidから"
                else -> ""
            }

            val layoutcolor: Int
            when (item?.sendStatus) {
                EnumSendStatus.DRAFT -> layoutcolor = Color.GRAY
                EnumSendStatus.OPEN -> {
                    layoutcolor = Color.WHITE
                }
                else -> layoutcolor = Color.WHITE
            }
            view.SendLayout.setBackgroundColor(layoutcolor)

            return view
        }


    }

    private class SpinnerAdapter(context: Context, list: Array<String>) :
        ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val items = getItem(position)
            val view = convertView
                ?: LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_spinner_item, parent, false)
            val item = view.findViewById<TextView>(android.R.id.text1)
            item.text = items
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val items = getItem(position)
            val view = convertView
                ?: LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_spinner_item, parent, false)
            val item = view.findViewById<TextView>(android.R.id.text1)
            item.text = items
            return view
        }
    }


}