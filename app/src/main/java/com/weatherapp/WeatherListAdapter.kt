package com.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.database.WeatherObject

class WeatherListAdapter (private val allWeatherData: ArrayList<WeatherObject>) : RecyclerView.Adapter<WeatherListAdapter.ViewHolder> () {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val cityNameTextView = itemView.findViewById<TextView>(R.id.city)
        val tempTextView = itemView.findViewById<TextView>(R.id.temperature)
        val icTextView = itemView.findViewById<TextView>(R.id.icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allWeatherData.size
    }

    override fun onBindViewHolder(holder: WeatherListAdapter.ViewHolder, position: Int) {
        val weatherObject: WeatherObject = allWeatherData[position]

        val cityTextView = holder.cityNameTextView
        cityTextView.text= weatherObject.name

        val temperatureTextView = holder.tempTextView
        temperatureTextView.text = weatherObject.temp.toString()

        val iconTextView = holder.icTextView
        iconTextView.text = weatherObject.icon
    }
}