package com.example.weatherapp_lu1_second

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class AccuWeatherLogoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_accu_weather_logo, container, false)
        val imageView = view.findViewById<ImageView>(R.id.iv_accuweather)
        //add an event handler to open the AccuWeather website
        imageView.setOnClickListener{
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.accuweather.com/")
            )
            startActivity(intent)
        }
        return view
    }

}