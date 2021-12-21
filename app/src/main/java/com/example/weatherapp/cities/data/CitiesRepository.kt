package com.example.weatherapp.cities.data

import javax.inject.Inject

class CitiesRepository @Inject constructor() {

    fun fetchCities() = listOf(
        "Gothenburg",
        "Stockholm",
        "Mountain View",
        "London",
        "New York",
        "Berlin"
    )
}