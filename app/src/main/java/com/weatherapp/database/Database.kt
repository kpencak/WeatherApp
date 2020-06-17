package com.weatherapp.database

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class Database(context: Context?) : SQLiteAssetHelper(
    context,
    DATABASE_NAMES,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAMES = "weather.db"
        private const val DATABASE_VERSION = 3
    }
}