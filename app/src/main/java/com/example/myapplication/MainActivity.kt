package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Hw.Constraint_Activity
import com.example.myapplication.okhttphw.retrofit
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), SampleFragmentDialog.DialogResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("onCreate")
        val Homework: Button = findViewById(R.id.btnHomework)
        val linearLayout: Button = findViewById(R.id.btnLinearLayout)
        Homework.setOnClickListener {
            Timber.d("onclick")
            startActivity(Intent(this, Constraint_Activity::class.java))
        }
        linearLayout.setOnClickListener {
            Timber.d("onclick2")
            startActivity(Intent(this, LinearActiviy::class.java))
        }
        btnSpinner.setOnClickListener {
            startActivity(Intent(this, SpinnerActivity::class.java))
        }
        btnIntent.setOnClickListener {
            startActivity(Intent(this, IntentActivity::class.java))
        }
        btnhwintent.setOnClickListener {
            startActivity(Intent(this, HwIntentActivity::class.java))
        }
        btnRoom.setOnClickListener {
            startActivity(Intent(this, RoomActivity::class.java))
        }
        btnGson.setOnClickListener {
            startActivity((Intent(this, GsonActivity::class.java)))
        }
        btnokhttp.setOnClickListener {
            startActivity((Intent(this, retrofit::class.java)))
        }
        btnfragment.setOnClickListener {
            startActivity(Intent(this, FragnebtActivity::class.java))
        }
        btndialogshow.setOnClickListener {
            val dialog = SampleFragmentDialog.makeDialog("保存しますか？")
            supportFragmentManager
                .beginTransaction()
                .add(dialog, SampleFragmentDialog::class.java.simpleName)
                .commit()
        }
        btntablayout.setOnClickListener {
            startActivity(Intent(this, TabLayoutActivity::class.java))
        }
    }

    override fun onSave() {
        Toast.makeText(this, "fragmentdialog", Toast.LENGTH_SHORT).show()
    }

    override fun onCancel() {

    }
}