package com.example.weatherapp.weatherdetails.network.model

import com.google.gson.annotations.SerializedName

data class WeatherDetailsResponseModel(
    @SerializedName("consolidated_weather")
    val weatherDetailsList: List<WeatherDetailsModel>
)
