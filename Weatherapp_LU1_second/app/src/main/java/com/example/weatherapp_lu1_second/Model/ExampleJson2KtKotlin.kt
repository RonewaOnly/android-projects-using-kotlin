package com.example.weatherapp_lu1_second.Model

import com.google.gson.annotations.SerializedName


data class ExampleJson2KtKotlin (

  @SerializedName("Headline"       ) var Headline       : Headline?                 = Headline(),
  @SerializedName("DailyForecasts" ) var DailyForecasts : ArrayList<DailyForecasts> = arrayListOf()

)