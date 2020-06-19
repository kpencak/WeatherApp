package com.weatherapp

import android.content.res.Configuration
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapp.database.WeatherObject
import com.weatherapp.database.WeatherOpenHelper
import kotlinx.android.synthetic.main.fragment_list.*


class MainActivity : AppCompatActivity() {

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
    }

}
