package com.weatherapp

import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.weatherapp.API.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private var weatherData: TextView? = null


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherData = findViewById(R.id.city)
        val typeface = Typeface.createFromAsset(assets, "Lato-Bold.ttf")

        val weatherFragment = WeatherFragment()
        val weatherListFragment = WeatherListFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, weatherFragment)
            commit()
        }

        val loadListImage = findViewById<ImageView>(R.id.load)

        loadListImage.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, weatherListFragment)
                addToBackStack(null)
                commit()
                currentData
            }
        }
    }
    val currentData: Unit
        get() {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(MainActivity.Companion.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(WeatherService::class.java)
            val call = service.getCurrentWeatherData(
                MainActivity.Companion.lat,
                MainActivity.Companion.lon,
                MainActivity.Companion.AppId
            )
            call.enqueue(object : Callback<WeatherResponse?> {
                override fun onResponse(
                    call: Call<WeatherResponse?>,
                    response: Response<WeatherResponse?>
                ) {
                    if (response.code() == 200) {
                        val weatherResponse = response.body()!!
                        val stringBuilder = "Country: " +
                                weatherResponse.sys.country +
                                "\n" +
                                "Temperature: " +
                                weatherResponse.main.temp +
                                "\n" +
                                "Temperature(Min): " +
                                weatherResponse.main.temp_min +
                                "\n" +
                                "Temperature(Max): " +
                                weatherResponse.main.temp_max +
                                "\n" +
                                "Humidity: " +
                                weatherResponse.main.humidity +
                                "\n" +
                                "Pressure: " +
                                weatherResponse.main.pressure
                        weatherData!!.text = stringBuilder
                    }
                }

                override fun onFailure(
                    call: Call<WeatherResponse?>,
                    t: Throwable
                ) {
                    weatherData!!.text = t.message
                }
            })
        }

    companion object {
        var BaseUrl = "http://api.openweathermap.org/"
        var AppId = "2e65127e909e178d0af311a81f39948c"
        var lat = "35"
        var lon = "139"
    }
}
