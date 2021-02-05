package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FragnebtActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragnebt)
        val fragment = supportFragmentManager.findFragmentByTag(
            FirstFragment::class.java.simpleName
        ) ?: FirstFragment.newInstance("suzuki", "tarou")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentadd, fragment, FirstFragment::class.java.simpleName)
            .commit()
    }
}