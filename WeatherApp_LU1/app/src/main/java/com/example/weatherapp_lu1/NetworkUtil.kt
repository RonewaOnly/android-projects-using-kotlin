package com.example.weatherapp_lu1

import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class NetworkUtil {

    private val WEATHER_URL = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/305605"
    private val PARAM_METRIC = "metric"
    private val METRIC_VALUE ="true"
    private val PARAM_API_KEY = "apikey"
    private val LOGGING_TAG = "URLWECREATED"

    fun buildURLForWeather(): URL? {
        val buildUrl: Uri = Uri.parse(WEATHER_URL).buildUpon()
            .appendQueryParameter(PARAM_API_KEY, BuildConfig.ACCUWEATHER_KEY)
            .appendQueryParameter(PARAM_METRIC, METRIC_VALUE)
            .build()

       var uri: URL? = null
        try {
            uri = URL(buildUrl.toString())
        }catch (e: MalformedURLException){
            e.printStackTrace()
        }
        Log.i(LOGGING_TAG,"BuildURLForWeather: $uri")
        return uri
    }

    suspend fun fetchWeatherData(url: URL): String? = withContext(Dispatchers.IO) {
        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()
            reader.forEachLine { response.append(it) }
            reader.close()
            response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}