package com.example.weatherapp.weatherdetails.ui

data class WeatherViewState(
    val temp: String,
    val minMaxTemp: String,
    val weatherStateName: String,
    val date: String,
    val windSpeed: String,
    val pressure: String,
    val humidity: String,
    val visibility: String,
    val predictability: String,
    val weatherIcon: String
)
