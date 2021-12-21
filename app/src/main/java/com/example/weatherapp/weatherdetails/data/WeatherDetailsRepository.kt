package com.example.weatherapp.weatherdetails.data

import com.example.weatherapp.weatherdetails.network.Loadable
import com.example.weatherapp.weatherdetails.network.model.WeatherDetailsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeatherDetailsRepository @Inject constructor(private val weatherDetailsService: WeatherDetailsService) {
   suspend fun fetchWeatherDetails(city: String): Flow<Loadable<WeatherDetailsModel>> {
        return weatherDetailsService.fetchWeatherDetails(city)
    }

}
