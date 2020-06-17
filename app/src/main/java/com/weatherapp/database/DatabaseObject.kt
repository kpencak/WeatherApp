package com.weatherapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

open class DatabaseObject(context: Context?) {
    val dbConnection: SQLiteDatabase?

    fun closeDbConnection() {
        dbConnection?.close()
    }

    companion object {
        private lateinit var dbHelper: Database
    }

    init {
        dbHelper = Database(context)
        dbHelper.writableDatabase
        dbConnection = dbHelper.readableDatabase
    }
}