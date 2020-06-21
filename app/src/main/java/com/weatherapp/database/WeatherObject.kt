package com.weatherapp.database

class WeatherObject {
    var id: Int = 0
    var name: String? = null
    var icon: String? = null
    var temp: String? = null

    constructor(name: String, icon: String, temp: String) {
        this.name = name
        this.icon = icon
        this.temp = temp
    }
}