package com.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeatherListAdapter (private val mWeatherList: ArrayList<WeatherResponse>) : RecyclerView.Adapter<WeatherListAdapter.ViewHolder> () {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val cityNameTextView = itemView.findViewById<TextView>(R.id.city)
        val tempTextView = itemView.findViewById<TextView>(R.id.temperature)
        val humTextView = itemView.findViewById<TextView>(R.id.humidity)
        val icTextView = itemView.findViewById<TextView>(R.id.icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
//        return mWeatherList.size
        return 0
    }

    override fun onBindViewHolder(holder: WeatherListAdapter.ViewHolder, position: Int) {
        val weatherObject: WeatherResponse = mWeatherList[position]

        val cityTextView = holder.cityNameTextView
        cityTextView.text= weatherObject.name

        val temperatureTextView = holder.tempTextView
        temperatureTextView.text = weatherObject.main?.temp.toString()

        val humidityTextView = holder.humTextView
        humidityTextView.text = weatherObject.main?.humidity.toString()

        val iconTextView = holder.icTextView
        iconTextView.text = weatherObject.weather[0].icon
    }
}