package com.example.myapplication.Hw

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.myapplication.R
import com.example.myapplication.UserDatabase
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_constraint_.ListViewSendInfo
import kotlinx.android.synthetic.main.activity_constraint_.btnSend
import kotlinx.android.synthetic.main.activity_constraint_.editTextMessage
import kotlinx.android.synthetic.main.activity_constraint_.rbtnSendAndroid
import kotlinx.android.synthetic.main.activity_constraint_.rbtnSendPC
import kotlinx.android.synthetic.main.activity_constraint_.spinnersend
import kotlinx.android.synthetic.main.item_homework_sendinfo.view.SendLayout
import kotlinx.android.synthetic.main.item_homework_sendinfo.view.SendName
import kotlinx.android.synthetic.main.item_homework_sendinfo.view.SendTimeDate
import kotlinx.android.synthetic.main.item_homework_sendinfo.view.Sendmsg
import kotlinx.android.synthetic.main.item_homework_sendinfo.view.sendtool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.util.Date

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

    private val msgs = ArrayList<Addsendinfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_)
        //arrayadapter代入
        listviewadapter = SendInfoAdapter(this, msgs)
        //listviewアダプターをarrayadapter代入
        ListViewSendInfo.adapter = listviewadapter
        //contexualmenu
        registerForContextMenu(ListViewSendInfo)
        //ローカルDBをビルド
        db = Room.databaseBuilder(this, UserDatabase::class.java, "SAMPLE_DB").build()

        //データベースから取得
        getmsgs()
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
                deletemsg(msgs[position])
            }

            R.id.Configure -> {
                val sendInfo = msgs.get(position)
                val editintent = Intent(this, HwEditActivity::class.java).apply {
                    putExtra(HwEditActivity.KEY_SENDINFO, sendInfo)
                }
                startActivityForResult(editintent, MSGEDIT_RESULT)
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            // 結果をチェックする
            return
        }

        when (requestCode) {
            MSGEDIT_RESULT -> {
                val sendInfo = data?.getParcelableExtra(HwEditActivity.KEY_SENDINFO) as? Addsendinfo
                if (sendInfo != null) {
                    updmsg(sendInfo)
                }
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

        msgs.add(info)
        listviewadapter.notifyDataSetChanged()

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
            }
        }
    }

    private fun getmsgs() {
        coroutinScope.launch(Dispatchers.Main) {
            msgs.clear()
            withContext(Dispatchers.IO) {
                msgs.addAll(db.addsendinfoDao().getAll())
            }
            listviewadapter.notifyDataSetChanged()
        }
    }

    private fun deletemsg(sendInfo: Addsendinfo) {
        coroutinScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                //削除処理
                db.addsendinfoDao().deleteMsg(sendInfo.uid)
            }
            msgs.remove(sendInfo)
            listviewadapter.notifyDataSetChanged()
        }
    }

    private fun updmsg(sendInfo: Addsendinfo) {
        coroutinScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                db.addsendinfoDao().update(sendInfo)
            }
            val index = msgs.indexOfFirst { it.uid == sendInfo.uid }
            if (index != -1) {
                msgs[index] = sendInfo
            }
            listviewadapter.notifyDataSetChanged()
        }
    }
}

private class SendInfoAdapter(context: Context, list: List<Addsendinfo>) :
    ArrayAdapter<Addsendinfo>(
        context,
        R.layout.item_homework_sendinfo,
        list
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

