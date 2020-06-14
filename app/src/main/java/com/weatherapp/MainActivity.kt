package com.weatherapp

import android.content.res.Configuration
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
    }

}
