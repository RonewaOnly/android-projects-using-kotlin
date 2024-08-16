package com.example.weatherapp_lu1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.URL
data class WeatherResponse(val Headline: Headline, val DailyForecasts: List<Forecast>)
data class Headline(val EffectiveDate: String, val Text: String, val Link: String)
data class Forecast(val Date: String, val Temperature: Temperature)
data class Temperature(val Minimum: TempDetail, val Maximum: TempDetail)
data class TempDetail(val Value: Double, val Unit: String)

class WeatherViewModel:ViewModel() {

    private val networkUtil = NetworkUtil()

    private val _weatherData = MutableStateFlow<WeatherResponse?>(value = null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            val url: URL? = networkUtil.buildURLForWeather()
            if (url != null) {
                val response = networkUtil.fetchWeatherData(url)
                response?.let {
                    val weatherData = Gson().fromJson(it, WeatherResponse::class.java)
                    _weatherData.value = weatherData
                }
            }
        }
    }

}