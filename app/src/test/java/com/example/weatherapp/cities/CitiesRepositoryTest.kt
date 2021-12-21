package com.example.weatherapp.cities

import com.example.weatherapp.cities.data.CitiesRepository
import org.junit.Assert
import org.junit.Test

class CitiesRepositoryTest {

    private val citiesRepository = CitiesRepository()

    @Test
    fun `when fetch cities - then get city list`() {
        val expectedCities = listOf(
            "Gothenburg",
            "Stockholm",
            "Mountain View",
            "London",
            "New York",
            "Berlin"
        )
        val actualCities = citiesRepository.fetchCities()
        Assert.assertEquals(expectedCities, actualCities)
    }
}