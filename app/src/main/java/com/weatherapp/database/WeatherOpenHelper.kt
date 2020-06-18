package com.weatherapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class WeatherOpenHelper (context: Context?, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_WEATHER_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_CITY + " VARCHAR(255)," +
                COLUMN_TEMP + " VARCHAR(255)," +
                COLUMN_ICON + " VARCHAR(255)" +
                ")")
        db.execSQL(CREATE_WEATHER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
        onCreate(db)
    }

    fun addWeather(weather: WeatherObject) {
        val values = ContentValues()
        values.put(COLUMN_CITY, weather.name)
        values.put(COLUMN_ICON, weather.icon)
        values.put(COLUMN_TEMP, weather.temp)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    val allWeatherData: ArrayList<WeatherObject> get() {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if(cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITY))
                val temp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEMP))
                val icon = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ICON))

                allWeatherData.add(WeatherObject(id, name, icon, temp))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return this.allWeatherData
    }

    fun deleteWeather(cityName: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_CITY=$cityName", null) > 0
        db.close()
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "weather.db"
        val TABLE_NAME = "weather"
        val COLUMN_ID = "_id"
        val COLUMN_CITY = "city"
        val COLUMN_ICON = "icon"
        val COLUMN_TEMP = "temperature"
    }
}