package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Hw.Addsendinfo
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_gson.*
import timber.log.Timber
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.Charset

class GsonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gson)

        val addsendinfo = Addsendinfo(
            0,
            "michael",
            "2021/1/13",
            "おはようございます",
            1,
            1
        )

        btnsavegson.setOnClickListener {
            val gson = GsonBuilder().create()
            val text = gson.toJson(addsendinfo)
            Timber.d(text)
            openFileOutput("sample.Json", MODE_PRIVATE)?.use {
                OutputStreamWriter(it, Charset.forName("UTF-8")).use { writer ->
                    writer.write(text)
                }
            }

        }

        btnloadgson.setOnClickListener {
            val gson = GsonBuilder().create()
            val info = openFileInput("sample.Json")?.use {
                InputStreamReader(it, Charset.forName("UTF-8")).use { reader ->
                    val text = reader.readText()
                    Timber.d(text)
                    gson.fromJson(text, Addsendinfo::class.java)
                }
            }

            if (info != null) {
                Timber.d(info.name)
                Timber.d(info.sendMessage)
            }
        }
    }
}