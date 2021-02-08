package com.example.myapplication.okhttphw

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TranslatetorApi {
    @Headers(
        "x-rapidapi-host: microsoft-translator-text.p.rapidapi.com",
        "x-rapidapi-key:5751daedb5msh4bfeaff6df41fa4p1abeadjsn5c0402510a37",
        "content-type:application/json",
        "accept:application/json"
    )
    @POST("/translate?to=ja&api-version=3.0&profanityAction=NoAction&textType=plain")
    suspend fun translate(@Body text: RequestBody): List<TranslateData>
}
