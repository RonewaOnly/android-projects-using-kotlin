package com.example.weatherapp_lu1_second.Model

import com.google.gson.annotations.SerializedName


data class Minimum (

  @SerializedName("Value"    ) var Value    : Double? = null,
  @SerializedName("Unit"     ) var Unit     : String? = null,
  @SerializedName("UnitType" ) var UnitType : Float?    = null

)