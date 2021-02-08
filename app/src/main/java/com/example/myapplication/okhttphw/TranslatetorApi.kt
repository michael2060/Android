package com.example.myapplication.okhttphw

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
    @POST("/translate?from=en&profanityAction=NoAction&textType=plain&to=ja&api-version=3.0")
    suspend fun translate(@Body text: @JvmSuppressWildcards List<Map<String, String>>): TranslateData
}
