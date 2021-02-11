package com.example.myapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.honda.logi.pjwms.fragment.Fragment_ocr
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class TabLayoutActivity : AppCompatActivity() {
    private var job = Job()
    private var coroutine = CoroutineScope(Dispatchers.Main + job)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val fragment = Fragment_ocr()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_linearlayout, fragment)
            .commit()

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}