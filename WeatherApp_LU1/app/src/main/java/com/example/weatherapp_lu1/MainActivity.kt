package com.example.weatherapp_lu1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp_lu1.ui.theme.WeatherApp_LU1Theme
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApp_LU1Theme {
                WeatherScreen()
            }
        }
    }
}


@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val weatherData = viewModel.weatherData.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        weatherData?.let { data ->
            Text(
                text = "Headline: ${data.Headline.Text}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Link: ${data.Headline.Link}",
                style = MaterialTheme.typography.bodyLarge
            )
            LazyColumn {
                items(data.DailyForecasts) { forecast ->
                    Text(
                        text = "Date: ${forecast.Date}, Min: ${forecast.Temperature.Minimum.Value} ${forecast.Temperature.Minimum.Unit}, Max: ${forecast.Temperature.Maximum.Value} ${forecast.Temperature.Maximum.Unit}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        } ?: run {
            Text(text = "Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherApp_LU1Theme {
        WeatherScreen()
    }
}