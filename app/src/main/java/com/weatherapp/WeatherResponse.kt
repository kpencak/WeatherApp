package com.weatherapp

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class WeatherResponse {
    @SerializedName("weather")
    var weather = ArrayList<Weather>()
    @SerializedName("main")
    var main: Main? = null
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("name")
    var name: String? = null
}

class Weather {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("icon")
    var icon: String? = null
}

class Main {
    @SerializedName("temp")
    var temp: Float = 0.toFloat()
    @SerializedName("humidity")
    var humidity: Float = 0.toFloat()
}


