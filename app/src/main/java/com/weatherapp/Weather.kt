package com.weatherapp
//
//import com.j256.ormlite.dao.Dao
//import com.j256.ormlite.field.DatabaseField
//import com.j256.ormlite.table.DatabaseTable
//import com.wtm.android.weatherapp.DatabaseHelper;
//
//@DatabaseTable(tableName = "weather")
data class Weather(
//    @DatabaseField(generatedId = true)
    val id: Int? = null,
//
//    @DatabaseField
    val cityName: String,
//
//    @DatabaseField
    val temperature: Int,
//
//    @DatabaseField
    val humidity: Int,
//
//    @DatabaseField
    val icon: String
//    val lat: Float,
//    val lon: Float
)
//
//class WeatherDao {
//
//    companion object {
//        lateinit var dao: Dao<Weather, Int>

//    }
//
//    init {
//        dao = DatabaseHelper.getDao(Weather::class.java)
//    }
//
//    fun add(weather: Weather) = dao.createOrUpdate(weather)
//
//    fun update(weather: Weather) = dao.update(weather)
//
//    fun delete(weather: Weather) = dao.delete(weather)
//
//}
