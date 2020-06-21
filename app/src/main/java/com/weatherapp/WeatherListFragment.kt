package com.weatherapp


import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.weatherapp.database.WeatherOpenHelper
import kotlinx.android.synthetic.main.fragment_list.*


class WeatherListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var db : SQLiteDatabase
    private lateinit var cursor : Cursor
    private lateinit var dbHandler: WeatherOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHandler = WeatherOpenHelper(activity, null)
        try {
            db = dbHandler.readableDatabase
            cursor = getAllWeather()
            val mAdapter = context?.let { WeatherListAdapter(it, cursor) }
            recycleViewList.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = mAdapter
            }

            ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                   return false
                }

                override fun onSwiped(viewHolder :RecyclerView.ViewHolder, direction:Int) {
                    var id = viewHolder.itemView.tag as Int
                    dbHandler.deleteWeather(id)
                    mAdapter?.swapCursor(getAllWeather())
                    onResume()
                }
            }).attachToRecyclerView(recycleViewList)
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

    fun getAllWeather() : Cursor {
        return db.query(
            WeatherOpenHelper.TABLE_NAME,
            arrayOf(
                WeatherOpenHelper.COLUMN_ID,
                WeatherOpenHelper.COLUMN_CITY,
                WeatherOpenHelper.COLUMN_TEMP,
                WeatherOpenHelper.COLUMN_ICON
            ),
            null, null, null, null, WeatherOpenHelper.COLUMN_ID
        )
    }
}
