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
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_constraint_.*
import kotlinx.android.synthetic.main.activity_linear_activiy.*
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

    private lateinit var msgs:List<Addsendinfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_)
        listviewadapter = SendInfoAdapter(this)
        ListViewSendInfo.adapter = listviewadapter
        db = Room.databaseBuilder(this, UserDatabase::class.java, "SAMPLE_DB").build()

        //データベースから取得
        coroutinScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO) {
                msgs = db.addsendinfoDao().getAll()
                }
            //取得後メッセージを追加
            for (msg: Addsendinfo in msgs) {
                listviewadapter.add(msg)
            }
        }

        ListViewSendInfo.setOnItemClickListener {
                parent, view, position, id ->
                coroutinScope.launch(Dispatchers.Main){
                withContext(Dispatchers.IO) {
                    //削除処理
                    db.addsendinfoDao().deleteMsg(msgs.get(position).uid)
                }
                listviewadapter.remove(ListViewSendInfo.getItemAtPosition(position) as Addsendinfo?)
            }
        }



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


        val info = Addsendinfo(0, name, datetime, message, sendtool.value, sendStatus.status)

        listviewadapter.add(info)

        if (spinnerstatus == EnumSendStatus.OPEN) {
            val snackbar = Snackbar.make(btnSend, "公開しました。", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("OK") {
            }.show()
        }
        editTextMessage.text = null

        //データベースに追加
        coroutinScope.launch {
            withContext(Dispatchers.IO) {
                db.addsendinfoDao().insert(info)
                msgs=  db.addsendinfoDao().getAll()
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
                EnumSendtool.PC.value -> "PCから"
                EnumSendtool.Android.value -> "Androidから"
                else -> ""
            }

            val layoutcolor: Int
            when (item?.sendStatus) {
                EnumSendStatus.DRAFT.status -> layoutcolor = Color.GRAY
                EnumSendStatus.OPEN.status -> {
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