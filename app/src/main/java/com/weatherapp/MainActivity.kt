package com.weatherapp

import android.content.Context
import android.content.res.Configuration

import android.location.LocationManager
import android.location.Geocoder
import java.util.Locale

import android.os.Bundle
import android.util.Log

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.weatherapp.database.WeatherObject
import com.weatherapp.database.WeatherOpenHelper
import java.lang.IllegalStateException


class MainActivity : AppCompatActivity() {

    private var locationManager: LocationManager? = null
    private var PERMISSION_ID = 45

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherFragment = WeatherFragment()
        val weatherListFragment = WeatherListFragment()

        val loadListImage = findViewById<ImageView>(R.id.load)
        val saveItemImage = findViewById<ImageView>(R.id.save)
        val locationImage = findViewById<ImageView>(R.id.location)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID)


        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, weatherFragment)
                    commit()
                }
            }


            loadListImage.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, weatherListFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, weatherFragment)
                replace(R.id.flFragment2, weatherListFragment)
                commit()
            }
        }


        saveItemImage.setOnClickListener {
            try {
                val dbHandler = WeatherOpenHelper(this, null)

                val fragment = supportFragmentManager.findFragmentById(R.id.flFragment)
                val fragmentView = fragment?.view

                val cityNameText = fragmentView?.findViewById<EditText>(R.id.city)
                val cityName = cityNameText?.text.toString()

                val iconTextView = fragmentView?.findViewById<TextView>(R.id.icon)
                val icon = iconTextView?.text.toString()

                val tempTextView = fragmentView?.findViewById<TextView>(R.id.temperature)
                val temp = tempTextView?.text.toString()

                val weatherObject = WeatherObject(cityName, icon, temp)
                dbHandler.addWeather(weatherObject)
                Toast.makeText(this, "Added to database", Toast.LENGTH_LONG).show()

                val fragment2 = supportFragmentManager.findFragmentById(R.id.flFragment2)
                fragment2?.onResume()
            } catch (e: ClassCastException) {
                Log.e("WARNING", "Tried to reach save from list view")
            }
        }


        locationImage.setOnClickListener{
            try {
                val fragment = supportFragmentManager.findFragmentById(R.id.flFragment)
                val fragmentView = fragment?.view
                val cityNameText = fragmentView?.findViewById<EditText>(R.id.city)

                try {
                    val location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    var geocoder = Geocoder(baseContext, Locale.getDefault())
                    var address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    cityNameText?.setText(address[0].locality)
                } catch (e: SecurityException) {
                    Toast.makeText(this, "Can't get location", Toast.LENGTH_LONG).show()
                    Log.e("ERROR", e.toString())
                } catch (e: IllegalStateException) {
                    Toast.makeText(this, "Can't get location", Toast.LENGTH_LONG).show()
                    Log.e("ERROR", e.toString())
                }
            } catch (e : ClassCastException) {
                Log.e("WARNING", "Tried to reach out location from list view")
            }
        }

    }
}


