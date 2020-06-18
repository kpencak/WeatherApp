package com.weatherapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val mWeatherList = dbHandler.allWeatherData

        recycleViewList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = WeatherListAdapter(mWeatherList)
        }
    }

    companion object {
        fun newInstance(): WeatherFragment = WeatherFragment()
    }
}
