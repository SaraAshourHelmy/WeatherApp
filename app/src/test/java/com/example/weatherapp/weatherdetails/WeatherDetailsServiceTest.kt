package com.example.weatherapp.weatherdetails

import com.example.weatherapp.weatherdetails.data.WeatherDetailsService
import com.example.weatherapp.weatherdetails.network.Loadable
import com.example.weatherapp.weatherdetails.network.WeatherApi
import com.example.weatherapp.weatherdetails.network.model.CityModel
import com.example.weatherapp.weatherdetails.network.model.WeatherDetailsModel
import com.example.weatherapp.weatherdetails.network.model.WeatherDetailsResponseModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import java.lang.RuntimeException

class WeatherDetailsServiceTest {

    private val weatherApi: WeatherApi = mock()
    private val service = WeatherDetailsService(weatherApi)
    private val city = "London"
    private val cityId = 44418
    private val weatherDetailsModel: WeatherDetailsModel = mock()
    private val weatherResponse =
        WeatherDetailsResponseModel(listOf(weatherDetailsModel, weatherDetailsModel))
    private val cityModelList: List<CityModel> = mock()
    private val cityModel: CityModel = mock()


    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch weather details  - then call fetch city model from weather api`() =
        runBlockingTest {
            service.fetchWeatherDetails(city).collect()
            verify(weatherApi, times(1)).fetchCityModel(city)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch weather details  - then call fetch weather details from weather api`() =
        runBlockingTest {
            mockSuccessResponse()
            service.fetchWeatherDetails(city).collect()
            verify(weatherApi, times(1)).fetchWeatherDetails(cityId)
        }


    @ExperimentalCoroutinesApi
    @Test
    fun `when start fetching  weather details - then emit loading`() = runBlockingTest {
        val actualResult = service.fetchWeatherDetails(city)
        val expectedResult = flow { emit(Loadable.Loading) }
        Assert.assertEquals(expectedResult.first(), actualResult.first())
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch weather details successfully - then get weather entity`() = runBlockingTest {
        mockSuccessResponse()
        val actualResult = service.fetchWeatherDetails(city)
        val expectedResult = flow { emit(Loadable.Success(weatherDetailsModel)) }
        Assert.assertEquals(expectedResult.first(), actualResult.last())

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch weather details and it has an error - then emit error`() = runBlockingTest {
        mockErrorResponse()
        val actualResult = service.fetchWeatherDetails(city)
        val expectedResult = flow { emit(Loadable.Error) }
        Assert.assertEquals(expectedResult.first(), actualResult.last())
    }

    @ExperimentalCoroutinesApi
    private fun mockSuccessResponse() = runBlockingTest {
        whenever(weatherApi.fetchCityModel(city)).thenReturn(cityModelList)
        whenever(cityModelList[0]).thenReturn(cityModel)
        whenever(cityModel.id).thenReturn(cityId)
        whenever(weatherApi.fetchWeatherDetails(cityId)).thenReturn(
            weatherResponse
        )
    }

    @ExperimentalCoroutinesApi
    private fun mockErrorResponse() = runBlockingTest {
        whenever(weatherApi.fetchCityModel(city)).thenThrow(
            RuntimeException()
        )
    }

}