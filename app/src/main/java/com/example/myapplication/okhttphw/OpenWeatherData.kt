package com.example.myapplication.okhttphw

import com.google.gson.annotations.SerializedName

data class OwmWeather(
        var id: Int = 0,
        var main: String? = null,
        var description: String? = null,
        var icon: String? = null
)

data class OwmMain(
        var temp: Float = 0F,
        @SerializedName("temp_min")
        var tempMin: Float = 0F,
        @SerializedName("temp_max")
        var tempMax: Float = 0F,
        var humidity: Int = 0
)

data class OpenWeatherMapData(
        var weather: List<OwmWeather>? = null,
        var main: OwmMain? = null,
        var name: String? = null
)
