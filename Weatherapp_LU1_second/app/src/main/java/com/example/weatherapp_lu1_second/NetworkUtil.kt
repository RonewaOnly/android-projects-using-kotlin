package com.example.weatherapp_lu1_second

import android.net.Uri
import android.util.Log
import java.net.MalformedURLException
import java.net.URL

class NetworkUtil {
    companion object {
        private const val WEATHER_URL = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/305605"
        private const val PARAM_METRIC = "metric"
        private const val METRIC_VALUE = "true"
        private const val PARAM_API_KEY = "apikey"
        private const val LOGGING_TAG = "URLWECREATED"
    }

    fun buildURLForWeather(): URL? {
        val buildUri: Uri = Uri.parse(WEATHER_URL).buildUpon()
            .appendQueryParameter(PARAM_API_KEY, BuildConfig.ACCUWEATHER_API_KEY)
            .appendQueryParameter(PARAM_METRIC, METRIC_VALUE)
            .build()

        var url: URL? = null
        try {
            url = URL(buildUri.toString())
        } catch (e: MalformedURLException) {
            Log.e(LOGGING_TAG, "Error creating URL", e)
        }

        Log.i(LOGGING_TAG, "BuildURLForWeather: $url")
        return url
    }
}
