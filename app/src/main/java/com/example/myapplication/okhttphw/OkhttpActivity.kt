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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File

class OkhttpActivity : AppCompatActivity() {
    companion object {
        private const val WEATHER_API_KEY = "c42bc6d679fc62dd60c6002b8bdfda59"
        private const val WEATHER_ICON_URI = "https://openweathermap.org/img/wn/%s@2x.png"
        private const val ICON_DIR = "icons"
    }

    private var iconuri: String = ""
        set(value) {
            field = "https://openweathermap.org/img/wn/${value}@2x.png"
        }

    private val client = OkHttpClient()
    private val job = Job()
    private val coroutine = CoroutineScope(Dispatchers.Main + job)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp)


        //更新イベント
        btnweaterupdate.setOnClickListener {
            coroutine.launch(Dispatchers.Main) {
                val data = loadweather()
                imgweathericon.setImageBitmap(loadIcon(data.weather?.get(0)?.icon.toString()))
                txtplace.text = data.name
                txttemp.text = data.main?.temp.toString()
                txthumid.text = data.main?.humidity.toString()


            }
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

        val weather = json.getAsJsonArray("weather")
        val icon = weather[0].asJsonObject.get("icon").asString

        //アイコン取得
//        withContext(Dispatchers.IO) {
//            iconuri = "04d"
//            bitmap =
//                    BitmapFactory.decodeStream(URL(iconuri).openConnection().getInputStream())
//        }

        //UIを更新
        val cachedir = cacheDir
        val iconpath = cachedir.toString() + "\\${icon}.png"
        val iconfile = File(cachedir, "\\${icon}.png")

        val bitmap = if (iconfile.exists()) {
            BitmapFactory.decodeFile(iconpath)
        } else {
            loadIcon(icon)
        }


        imgweathericon.setImageBitmap(bitmap)
        txtplace.text = name.toString()
        txttemp.text = temp.toString()
        txthumid.text = humid.toString()
    }

    // suspend functionは、非同期処理でも、戻り値としてデータを返せます
    suspend fun loadIcon(icon: String): Bitmap? {
        // アイコン名のような短いファイル名の場合、他のデータと被る可能性があるので、フォルダを分けておくと良いです
        // 必須ではありません
        // weather_icon_%s.png のようなファイル名にして被らないようにする方法もあります
        val iconDir = File(cacheDir, ICON_DIR)
        if (!iconDir.exists()) {
            iconDir.mkdirs()
        }

        val fileName = "%s.png".format(icon)
        val file = File(iconDir, fileName)

        if (!file.exists()) {
            Timber.d("icon download %s", icon)
            val request = Request.Builder()
                .url(WEATHER_ICON_URI.format(icon))
                .build()
            withContext(Dispatchers.IO) {
                // Streamを使う時は、useを使うことで、解放忘れを防止できます
                client.newCall(request).execute().body?.byteStream()?.use { input ->
                    // 色々な所でファイル名を直接書くと間違えやすいので、定義して使うようにします
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }

        return withContext(Dispatchers.IO) {
            BitmapFactory.decodeFile(file.path)
        }
    }

    private suspend fun loadweather(): OpenWeatherMapData {
        return withContext(Dispatchers.IO) {
            val retrservice = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/")
                .build().create(OpenWeatherMapService::class.java);

            retrservice.weather(
                WEATHER_API_KEY,
                "singapore",
                "ja",
                "metric"
            )

        }

    }


}
