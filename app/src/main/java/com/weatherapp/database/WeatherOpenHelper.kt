package com.weatherapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.weatherapp.WeatherListAdapter

class WeatherOpenHelper (context: Context?, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_WEATHER_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT," +
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

    fun deleteWeather(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=\"$id\"", null) > 0
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