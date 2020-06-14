package com.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeatherListAdapter (private val mWeatherList: ArrayList<Weather>) : RecyclerView.Adapter<WeatherListAdapter.ViewHolder> () {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val cityNameTextView = itemView.findViewById<TextView>(R.id.city)
        val tempTextView = itemView.findViewById<TextView>(R.id.temperature)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mWeatherList.size
    }

    override fun onBindViewHolder(holder: WeatherListAdapter.ViewHolder, position: Int) {
        val weather: Weather = mWeatherList[position]
        val cityTextView = holder.cityNameTextView
        cityTextView.text= weather.cityName

        val temperatureTextView = holder.tempTextView
        temperatureTextView.text = weather.temperature.toString()
    }
}