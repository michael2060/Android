package com.example.myapplication.Hw

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.myapplication.R
import com.example.myapplication.UserDatabase
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_constraint_.*
import kotlinx.android.synthetic.main.item_homework_sendinfo.view.*
import kotlinx.coroutines.*
import java.text.DateFormat
import java.util.*

class Constraint_Activity : AppCompatActivity() {
    //定数
    companion object {
        const val MSGEDIT_RESULT = 1
    }

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

    private lateinit var msgs: List<Addsendinfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_)
        //arrayadapter代入
        listviewadapter = SendInfoAdapter(this)
        //listviewアダプターをarrayadapter代入
        ListViewSendInfo.adapter = listviewadapter
        //contexualmenu
        registerForContextMenu(ListViewSendInfo)
        //ローカルDBをビルド
        db = Room.databaseBuilder(this, UserDatabase::class.java, "SAMPLE_DB").build()

        //データベースから取得
        getmsgs(true)
        //アダプターにデータ追加

        /*ListViewSendInfo.setOnItemLongClickListener { parent, view, position, id ->
            coroutinScope.launch(Dispatchers.Main) {

                withContext(Dispatchers.IO) {
                    //削除処理
                    db.addsendinfoDao().deleteMsg(msgs.get(position).uid)
                }
                listviewadapter.remove(ListViewSendInfo.getItemAtPosition(position) as Addsendinfo?)
            }

            return@setOnItemLongClickListener true
        }*/


        //sendbtnclick event
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

        spinnerAdapter =
            SpinnerAdapter(this, spinnerlist)

        spinnersend.adapter = spinnerAdapter

        rbtnSendPC.isChecked = true
    }

    //option Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //contextual menu
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val index = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = index.position
        when (item.itemId) {
            R.id.delete -> {
                deletemsg(position)
                getmsgs(false)
            }

            R.id.Configure -> {
                val extra = msgs.get(position)
                val editintent = Intent(this, HwEditActivity::class.java).apply {
                    putExtra("uid", extra.uid)
                    putExtra("position", position)
                    putExtra("Name", extra.sendTime)
                    putExtra("msg", extra.sendMessage)
                }
                startActivityForResult(editintent, MSGEDIT_RESULT)
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            MSGEDIT_RESULT ->
                if (data != null) {
                    val uid = data.getIntExtra("uid", 0)
                    val position = data.getIntExtra("position", 0)
                    val msg = data.getStringExtra("msg")
                    val time = data.getStringExtra("time")

                    updmsg(uid, position, msg, time)

                }


        }
    }


    fun Sendbtnclick(
        name: String,
        datetime: String,
        message: String,
        sendtool: EnumSendtool,
        sendStatus: EnumSendStatus
    ) {


        val info = Addsendinfo(
            0,
            name,
            datetime,
            message,
            sendtool.value,
            sendStatus.status
        )

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
                msgs = db.addsendinfoDao().getAll()
            }
        }
    }

    private fun getmsgs(addlistflg: Boolean) {
        when (addlistflg) {
            true -> {
                coroutinScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        msgs = db.addsendinfoDao().getAll()
                    }
                    listviewadapter.addAll(msgs)
                }
            }
            false -> {
                coroutinScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        msgs = db.addsendinfoDao().getAll()
                    }
                }
            }
        }
    }

    private fun deletemsg(index: Int) {
        coroutinScope.launch(Dispatchers.Main) {

            withContext(Dispatchers.IO) {
                //削除処理
                db.addsendinfoDao().deleteMsg(msgs.get(index).uid)
            }
            listviewadapter.remove(ListViewSendInfo.getItemAtPosition(index) as Addsendinfo?)
        }
    }

    private fun updmsg(uid: Int?, position: Int?, msg: String?, time: String?) {
        coroutinScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                if (uid != null && msg != null && time != null) {
                    db.addsendinfoDao().updMsg(uid, msg, time)
                }
                if (position != null) {
                    val upd = listviewadapter.getItem(position)
                    if (upd != null) {
                        upd.sendTime = time
                        upd.sendMessage = msg
                    }
                }
            }
            listviewadapter.notifyDataSetChanged()
        }
    }
}


private class SendInfoAdapter(context: Context) :
    ArrayAdapter<Addsendinfo>(
        context,
        R.layout.item_homework_sendinfo
    ) {
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

