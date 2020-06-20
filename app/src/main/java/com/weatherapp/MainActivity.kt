package com.weatherapp

import android.content.Context
import android.content.res.Configuration
import android.location.*
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weatherapp.database.WeatherObject
import com.weatherapp.database.WeatherOpenHelper
import kotlinx.android.synthetic.main.fragment_list.*
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    private var locationManager: LocationManager? = null
    private var PERMISSION_ID = 45

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherFragment = WeatherFragment()
        val weatherListFragment = WeatherListFragment()

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, weatherFragment)
                    commit()
                }
            }

            val loadListImage = findViewById<ImageView>(R.id.load)

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

        val saveItemImage = findViewById<ImageView>(R.id.save)

        saveItemImage.setOnClickListener {
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
//            if (fragment2 != null && fragment2.isAdded){
//                fragment2.recycleViewList.layoutManager = LinearLayoutManager(this@MainActivity)
//                var position = fragment2.recycleViewList.adapter!!.itemCount
//                fragment2.recycleViewList.adapter?.notifyItemChanged(position)
//            }
            fragment2?.onResume()
        }

        val locationImage = findViewById<ImageView>(R.id.location)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID)

        locationImage.setOnClickListener{

            try {
                val location = locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
            } catch (e: SecurityException) {
                Toast.makeText(this, "Can't get location", Toast.LENGTH_LONG).show()
                Log.e("ERROR", e.toString())
            }

//                            val longitude = location.longitude
//                            val latitude = location.latitude
//
//                            var geocoder = Geocoder(applicationContext, Locale.getDefault())
//                            try {
//                                var listAddresses = geocoder.getFromLocation(latitude, longitude, 1)
//                                if (listAddresses.size > 0) {
////                                    cityNameText?.text = listAddresses.get(0).locality
//                                    System.out.print(listAddresses[0].locality)
//                                }
//                            } catch (e: IOException) {
//                                Toast.makeText(this, "Can't get location name", Toast.LENGTH_LONG).show()
//                            }
//                        }
//                    }
//            }
        }
    }
    private val locationListener: LocationListener = object : LocationListener {
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) { }
        override fun onProviderEnabled(provider: String?) { }
        override fun onProviderDisabled(provider: String?) { }
        override fun onLocationChanged(location: Location) {
            val fragment = supportFragmentManager.findFragmentById(R.id.flFragment)
            val fragmentView = fragment?.view

            val cityNameText = fragmentView?.findViewById<EditText>(R.id.city)

            var geocoder = Geocoder(baseContext, Locale.getDefault())
            var address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            cityNameText?.setText(address[0].locality)
        }
    }
}


