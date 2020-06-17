package com.weatherapp.database

import android.content.ContentValues
import android.content.Context
import java.util.ArrayList

class DatabaseQuery(context: Context) : DatabaseObject(context) {
    private val TABLE_NAME = "weather"
    private val KEY_NAME = "_id"

    val allWeatherData: List<WeatherObject>
        get() {
            val allWeatherData = ArrayList<WeatherObject>()
            val query = "Select * from weather"
            val cursor = this.dbConnection!!.rawQuery(query, null)

            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(0)
                    val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    val temp = cursor.getString(cursor.getColumnIndexOrThrow("temperature"))
                    val icon = cursor.getString(cursor.getColumnIndexOrThrow("icon"))

                    allWeatherData.add(WeatherObject(id, name, icon, temp))
                } while (cursor.moveToNext())
            }
            cursor.close()
            return allWeatherData
        }

    fun insertNewWeatherItem(cityName: String, icon: String, temp: String) {
        val values = ContentValues()
        values.put("name", cityName)
        values.put("icon", icon)
        values.put("temperature", temp)
        dbConnection!!.insert(TABLE_NAME, null, values)
    }

    fun deleteWeatherItem(weatherID: Int): Boolean {
        return dbConnection!!.delete(TABLE_NAME, "$KEY_NAME=$weatherID", null) > 0
    }
}