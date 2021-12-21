package com.example.weatherapp.weatherdetails.data

import com.example.weatherapp.weatherdetails.network.Loadable
import com.example.weatherapp.weatherdetails.network.WeatherApi
import com.example.weatherapp.weatherdetails.network.model.WeatherDetailsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class WeatherDetailsService @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun fetchWeatherDetails(city: String): Flow<Loadable<WeatherDetailsModel>> {
        return flow {
            emit(Loadable.Loading)
            val weatherEntity = fetchCityTomorrowWeather(city)
            emit(Loadable.Success(weatherEntity))
        }.catch {
            emit(Loadable.Error)
        }
    }

    private suspend fun fetchCityTomorrowWeather(city: String): WeatherDetailsModel {
        val cityModelList = weatherApi.fetchCityModel(city)
        val weatherDetailsModel = weatherApi.fetchWeatherDetails(cityModelList[0].id)
        return weatherDetailsModel.weatherDetailsList[1]
    }
}
