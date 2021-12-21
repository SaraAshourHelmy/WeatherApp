package com.example.weatherapp.weatherdetails

import com.example.weatherapp.weatherdetails.data.WeatherDetailsRepository
import com.example.weatherapp.weatherdetails.data.WeatherDetailsService
import com.example.weatherapp.weatherdetails.network.Loadable
import com.example.weatherapp.weatherdetails.network.model.WeatherDetailsModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class WeatherDetailsRepositoryTest {

    private val weatherDetailsService: WeatherDetailsService = mock()
    private val repository = WeatherDetailsRepository(weatherDetailsService)
    private val city = "London"
    private val weatherDetailsModel: WeatherDetailsModel = mock()
    private val successResponse = Loadable.Success(weatherDetailsModel)
    private val loadingResponse: Loadable.Loading = mock()
    private val errorResponse: Loadable.Error = mock()

    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch weather details - then get weather data from service`() = runBlockingTest {
        mockSuccessResponse()
        repository.fetchWeatherDetails(city)
        verify(weatherDetailsService, times(1)).fetchWeatherDetails(city)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch weather details - then get weather model successfully`() = runBlockingTest {
        mockSuccessResponse()
        val actualResponse = repository.fetchWeatherDetails(city)
        Assert.assertEquals(successResponse, actualResponse.first())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when start fetching weather details - then get loading response`() = runBlockingTest {
        mockLoadingResponse()
        val actualResponse = repository.fetchWeatherDetails(city)
        Assert.assertEquals(loadingResponse, actualResponse.first())

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch weather details and it has an error - then get error response`() =
        runBlockingTest {
            mockErrorResponse()
            val actualResponse = repository.fetchWeatherDetails(city)
            Assert.assertEquals(errorResponse, actualResponse.first())

        }

    @ExperimentalCoroutinesApi
    private fun mockSuccessResponse() = runBlockingTest {
        whenever(weatherDetailsService.fetchWeatherDetails(city)).thenReturn(
            flow {
                emit(successResponse)
            }
        )
    }

    @ExperimentalCoroutinesApi
    private fun mockLoadingResponse() = runBlockingTest {
        whenever(weatherDetailsService.fetchWeatherDetails(city)).thenReturn(
            flow {
                emit(loadingResponse)
            }
        )
    }

    @ExperimentalCoroutinesApi
    private fun mockErrorResponse() = runBlockingTest {
        whenever(weatherDetailsService.fetchWeatherDetails(city)).thenReturn(
            flow {
                emit(errorResponse)
            }
        )
    }
}