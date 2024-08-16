package com.example.weatherapp_lu1_second

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp_lu1_second.Model.ExampleJson2KtKotlin
import com.example.weatherapp_lu1_second.placeholder.PlaceholderContent
import com.google.gson.Gson
import kotlin.concurrent.thread

/**
 * A fragment representing a list of Items.
 */
class DailyForecastsFragment : Fragment() {

    private var columnCount = 1



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                val call = NetworkUtil()
                thread {
                    val weatherJSON = try{
                        call.buildURLForWeather()?.readText()
                    }catch (e: Exception){
                        return@thread
                    }
                    if(weatherJSON != null){
                        val gson = Gson()
                        val weatherData = gson.fromJson<ExampleJson2KtKotlin>(
                            weatherJSON,
                            ExampleJson2KtKotlin::class.java
                        )
                        activity?.runOnUiThread{
                            adapter = DailyForecastsRecyclerViewAdapter(weatherData.DailyForecasts)
                        }
                    }
                }
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            DailyForecastsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}