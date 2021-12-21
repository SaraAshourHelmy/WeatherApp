package com.example.weatherapp.weatherdetails.network

import com.example.weatherapp.weatherdetails.network.model.CityModel
import com.example.weatherapp.weatherdetails.network.model.WeatherDetailsResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherApi {

    @GET("api/location/search/")
    suspend fun fetchCityModel(@Query("query") cityName: String): List<CityModel>

    @GET("api/location/{id}")
    suspend fun fetchWeatherDetails(@Path("id") id: Int): WeatherDetailsResponseModel
}
