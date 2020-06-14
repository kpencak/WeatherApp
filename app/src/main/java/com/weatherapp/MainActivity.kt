package com.weatherapp

import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            }
        }
    }
}
