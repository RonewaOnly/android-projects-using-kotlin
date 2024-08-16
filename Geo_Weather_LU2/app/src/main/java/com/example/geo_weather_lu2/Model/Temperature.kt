package com.example.weatherapp_lu1_second.Model

import com.google.gson.annotations.SerializedName


data class Temperature (

  @SerializedName("Minimum" ) var Minimum : Minimum? = Minimum(),
  @SerializedName("Maximum" ) var Maximum : Maximum? = Maximum()

)