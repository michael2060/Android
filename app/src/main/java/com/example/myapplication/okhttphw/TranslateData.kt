package com.example.myapplication.okhttphw

data class TranslateData(
        var detectedLanguage: dlang,
        var translations: List<trandata>
)

data class dlang(
        var language: String,
        var score: Float = 0f
)

data class trandata(
        var text: String,
        var to: String
)