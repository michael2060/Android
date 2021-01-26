package com.example.myapplication.okhttphw

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_okhttp.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class OkhttpActivity : AppCompatActivity() {
    companion object {
        private const val WEATHER_API_KEY = "c42bc6d679fc62dd60c6002b8bdfda59"
        private const val WHEATHR_ICON_URI = "https://openweathermap.org/img/wn/04d@2x.png"
    }

    private var iconuri: String = ""
        set(value) {
            field = "https://openweathermap.org/img/wn/${value}@2x.png"
        }

    private val client = OkHttpClient()
    private val job = Job()
    private val coroutine = CoroutineScope(Dispatchers.Main + job)

    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp)

        //更新処理
        tempupdate()

        //更新イベント
        btnweaterupdate.setOnClickListener {
            tempupdate()
        }


    }

    suspend fun getweather(): String? {
        //apiからデータ取得
        val city = "Nagoya"
        val request = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?q=${city}&lang=ja&units=metric&appid=${WEATHER_API_KEY}")
            .build()
        val json = withContext(Dispatchers.IO) {
            client.newCall(request).execute().body?.string()
        }
        return json
    }

    fun tempupdate() {
        coroutine.launch(Dispatchers.Main) {
            val json = getweather()
            dataupd(json)
        }
    }

    suspend fun dataupd(jsonStringer: String?) {
        //データ格納
        val json = JsonParser.parseString(jsonStringer).asJsonObject
        val main = json.getAsJsonObject("main")

        val temp = main.getAsJsonPrimitive("temp").asLong
        val humid = main.getAsJsonPrimitive("humidity").asLong
        val name = json.get("name").asString

        //アイコン取得
        withContext(Dispatchers.IO) {
            iconuri = "04d"
            bitmap =
                BitmapFactory.decodeStream(URL(iconuri).openConnection().getInputStream())
        }

        //UIを更新
        imgweathericon.setImageBitmap(bitmap)
        txtplace.text = name.toString()
        txttemp.text = temp.toString()
        txthumid.text = humid.toString()
    }


}