package com.example.weatherapp.weatherdetails.network.model

import com.google.gson.annotations.SerializedName

data class CityModel(
    val title: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("woeid")
    val id: Int,
    @SerializedName("latt_long")
    val latLong: String
)