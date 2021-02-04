package com.example.myapplication.okhttphw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_retrofit.*
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class retrofit : AppCompatActivity() {

    private val client = OkHttpClient()
    private val job = Job()
    private val coroutine = CoroutineScope(Dispatchers.Main + job)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
        btnretrofitstart.setOnClickListener {
            coroutine.launch {
                val data = postTranslate("hi")
            }
        }
    }

    private suspend fun postTranslate(text: String): TranslateData {
        return withContext(Dispatchers.IO) {
            val mediaType = "application/json".toMediaTypeOrNull()
            val body: RequestBody = """[ {  "Text": "$text" }]""".toRequestBody(mediaType)
            val ret = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.cognitive.microsofttranslator.com/")
                .build().create(TranslatetorApi::class.java)
            ret.translate(body)
        }
    }
}