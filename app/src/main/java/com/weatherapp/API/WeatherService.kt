package com.weatherapp.API

import com.weatherapp.WeatherResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("q") q: String,
        @Query("APPID") app_id: String,
        @Query("units") units: String
    ): Call<WeatherResponse>
}