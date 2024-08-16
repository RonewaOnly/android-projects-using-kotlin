package com.example.weatherapp_lu1_second

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherapp_lu1_second.Model.ExampleJson2KtKotlin
import com.example.weatherapp_lu1_second.databinding.ActivityMainBinding
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
//    private lateinit var  viewing:TextView ;
//    private var fiveDay1List = mutableListOf<Forecast>()
//    private val LOGGING_TAG ="weatherData"
    lateinit var binding: ActivityMainBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCom pat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //This used to call the data raw without JSON and the thread
//        val classCall = NetworkUtil()
//        viewing  = findViewById(R.id.tv_weather)
//
//        val weatherThread = Thread {
//            val weather = try {
//                classCall.buildURLForWeather()?.readText()
//            } catch (e: Exception) {
//                e.printStackTrace()
//                return@Thread
//            }
//            runOnUiThread {
//                //viewing.text = weather
//                consumeJson(weather)
//            }
//        }
//        weatherThread.start()
    }
//    fun consumeJson(weatherJson: String?){
//        if(weatherJson != null){
//            val gson = Gson()
//            val weatherData = gson.fromJson<ExampleJson2KtKotlin>(weatherJson,
//                ExampleJson2KtKotlin::class.java)
//
//            for(forecast in weatherData.DailyForecasts){
//                viewing.append("Date: "+
//                    forecast.Date?.substring(0,10)+
//                    " Min: "+
//                    forecast.Temperature?.Minimum?.Value +
//                    " Max: "+
//                    forecast.Temperature?.Maximum?.Value+
//                    "\n"
//                )
//            }
//        }
//        if(fiveDay1List != null){
//            fiveDay1List.clear()
//        }
//
//        if(weatherJson != null){
//            try{
//                //Getting the json object
//                val rootWeatherData = JSONObject(weatherJson)
//                //find the daily weather forecasts array
//                val fiveDayForecast = rootWeatherData.getJSONArray("DailyForecasts")
//
//                //get the data from each entry in the array
//                for(i in 0 until fiveDayForecast.length()){
//                    val forecastObject = Forecast()
//                    val dailyWeather = fiveDayForecast.getJSONObject(i)
//
//                    //get Date
//                    val date = dailyWeather.getString("Date")
//                    Log.i(LOGGING_TAG,"consumeJson: Date:$date")
//                    forecastObject.date = date
//
//
//                    //get minimum temperature
//                    val temperatureObject = dailyWeather.getJSONObject("Temperature")
//                    val minTempObject = temperatureObject.getJSONObject("Minimum")
//                    val minTemp = minTempObject.getString("Value")
//                    Log.i(LOGGING_TAG,"consumeJSON: minTemp: $minTemp")
//                    forecastObject.minimumTemperature = minTemp
//
//                    //get maximum temperature
//                    val maxTempObject = temperatureObject.getJSONObject("Maximum")
//                    val maxTemp = maxTempObject.getString("Value")
//                    Log.i(LOGGING_TAG,"consumeJSON: maxTemp: $maxTemp")
//                    forecastObject.maximumTemperature = maxTemp
//                    fiveDay1List.add(forecastObject)
//
//                    viewing.append(
//                        "Date: $date, Min: $minTemp, Max: $maxTemp\n"
//                    )
//
//
//                }
//
//            }catch (e:JSONException){
//                    e.printStackTrace()
//            }
//        }
    //        }
}
