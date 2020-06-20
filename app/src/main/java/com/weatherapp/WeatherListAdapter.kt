package com.weatherapp

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.database.WeatherObject
import com.weatherapp.database.WeatherOpenHelper

class WeatherListAdapter(private val context: Context, private var cursor: Cursor): RecyclerView.Adapter<WeatherListAdapter.ViewHolder> () {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val cityNameTextView = itemView.findViewById<TextView>(R.id.city)
        val tempTextView = itemView.findViewById<TextView>(R.id.temperature)
        val icTextView = itemView.findViewById<TextView>(R.id.icon)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!cursor.moveToPosition(position)) {
            return
        }
            val name = cursor.getString(cursor.getColumnIndexOrThrow(WeatherOpenHelper.COLUMN_CITY))
            val temp = cursor.getString(cursor.getColumnIndexOrThrow(WeatherOpenHelper.COLUMN_TEMP))
            val icon = cursor.getString(cursor.getColumnIndexOrThrow(WeatherOpenHelper.COLUMN_ICON))
            val id = cursor.getInt(cursor.getColumnIndex(WeatherOpenHelper.COLUMN_ID))

//                Log.e("DEBUG", id.toString())
//                Log.e("DEBUG", name)
//                Log.e("DEBUG", temp)
//                Log.e("DEBUG", icon)

        holder.cityNameTextView.text = name
        holder.tempTextView.text = temp
        holder.icTextView.text = icon
        holder.itemView.tag = id
//
//        holder.deleteButton.setOnClickListener {
//            val dbHandler = WeatherOpenHelper(context, null)
//
//            dbHandler.deleteWeather(position)
//            notifyItemRemoved(position)
//        }
    }

    fun swapCursor(newCursor: Cursor) {
        if (cursor != null) {
            cursor.close()
        }

        cursor = newCursor

        if (newCursor != null) {
            notifyDataSetChanged()
        }
    }
}