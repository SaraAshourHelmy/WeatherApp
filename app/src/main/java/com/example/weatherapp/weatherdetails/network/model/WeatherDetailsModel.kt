package com.example.weatherapp.weatherdetails.network.model

import com.google.gson.annotations.SerializedName

data class WeatherDetailsModel(
    @SerializedName("id")
    val id: Long,
    @SerializedName("weather_state_name")
    val weatherStateName: String,
    @SerializedName("weather_state_abbr")
    val weatherStateAbbr: String,
    @SerializedName("wind_direction_compass")
    val windDirectionCompass: String,
    @SerializedName("created")
    val createdDate: String,
    @SerializedName("applicable_date")
    val applicableDate: String,
    @SerializedName("min_temp")
    val minTemp: Double,
    @SerializedName("max_temp")
    val maxTemp: Double,
    @SerializedName("the_temp")
    val temp: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_direction")
    val windDirection: Double,
    @SerializedName("air_pressure")
    val airPressure: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("visibility")
    val visibility: Double,
    @SerializedName("predictability")
    val predictability: Int
)