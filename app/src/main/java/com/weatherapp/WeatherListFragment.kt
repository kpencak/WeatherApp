package com.weatherapp


import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapp.R.attr.layoutManager
import com.weatherapp.database.WeatherObject
import com.weatherapp.database.WeatherOpenHelper
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * A simple [Fragment] subclass.
 *
 */
class WeatherListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var db : SQLiteDatabase
    private lateinit var cursor : Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_list, container, false)


    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbHandler = WeatherOpenHelper(activity, null)

        try {
            db = dbHandler.readableDatabase
            cursor = getAllWeather()

        recycleViewList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = WeatherListAdapter(context, cursor)
        }
        } catch (e: SQLiteException) {
            Toast.makeText(activity, "Database unavailable", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        val newCursor = getAllWeather()

        recycleViewList.adapter = context?.let { WeatherListAdapter(it, newCursor) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cursor.close()
        db.close()
    }

    companion object {
        fun newInstance(): WeatherFragment = WeatherFragment()
    }

    fun getAllWeather() : Cursor {
        return db.query(WeatherOpenHelper.TABLE_NAME,
            arrayOf(WeatherOpenHelper.COLUMN_ID,
                WeatherOpenHelper.COLUMN_CITY,
                WeatherOpenHelper.COLUMN_TEMP,
                WeatherOpenHelper.COLUMN_ICON),
            null, null, null, null, WeatherOpenHelper.COLUMN_ID)
    }
}
