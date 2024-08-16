package com.example.geo_weather_lu2

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.geo_weather_lu2.databinding.FragmentItemBinding
import com.example.weatherapp_lu1_second.Model.DailyForecasts

import com.example.weatherapp_lu1_second.placeholder.PlaceholderContent.PlaceholderItem


class DailyForecastsRecyclerViewAdapter(
    private val values: List<DailyForecasts>,
) : RecyclerView.Adapter<DailyForecastsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.date.text = item.Date?.substring(0,10)
        holder.minimum.text = item.Temperature?.Minimum?.Value.toString()
        holder.maximum.text = item.Temperature?.Maximum?.Value.toString()

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val date: TextView = binding.tvDate
        val minimum: TextView = binding.tvMinimum
        val maximum:TextView = binding.tvMaximum

        override fun toString(): String {
            return super.toString() + " '"+date.text + "'"
        }
    }

}