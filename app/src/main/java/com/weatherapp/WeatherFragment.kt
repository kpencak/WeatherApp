package com.weatherapp


import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.weatherapp.API.ServiceBuilder
import com.weatherapp.API.WeatherService
import kotlinx.android.synthetic.main.fragment_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 *
 */
class WeatherFragment : Fragment(R.layout.fragment_weather) {
    lateinit var weatherResponse: WeatherResponse
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(container?.context).inflate(R.layout.fragment_weather, container, false)


        var editText = view?.findViewById<TextView>(R.id.city)

        editText?.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    val request = ServiceBuilder.service
                    val call = request.getCurrentWeatherData(p0.toString(), AppId, units)

                    call.enqueue(object : Callback<WeatherResponse?> {
                        override fun onResponse(
                            call: Call<WeatherResponse?>,
                            response: Response<WeatherResponse?>
                        ) {
                            if (response.isSuccessful) {
                                weatherResponse = response.body()!!
                                temperature.text = weatherResponse.main?.temp.toString() + "° C"
                                humidity.text = weatherResponse.main?.humidity.toString() + "%"
                                icon.text = weatherResponse.weather[0].icon?.let { iconNameToChar(it) }

                            }
                        }

                        override fun onFailure(
                            call: Call<WeatherResponse?>,
                            t: Throwable
                        ) {
                            Log.e("ERROR", "Problem with response", t)
                        }
                    })
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            return view
        }

    companion object {
        var BaseUrl = "https://api.openweathermap.org/"
        var AppId = "2e65127e909e178d0af311a81f39948c"
        var units = "metric"
    }
}

fun iconNameToChar(icon: String): String? {
    return when (icon) {
        "01d" -> "\uf11b"
        "01n" -> "\uf110"
        "02d" -> "\uf112"
        "02n" -> "\uf104"
        "03d", "03n" -> "\uf111"
        "04d", "04n" -> "\uf111"
        "09d", "09n" -> "\uf116"
        "10d", "10n" -> "\uf113"
        "11d", "11n" -> "\uf10d"
        "13d", "13n" -> "\uf119"
        "50d", "50n" -> "\uf10e"
        else -> "E"
    }
}

