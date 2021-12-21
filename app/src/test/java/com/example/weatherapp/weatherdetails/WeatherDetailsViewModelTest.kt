package com.example.weatherapp.weatherdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.utils.MainCoroutineScopeRule
import com.example.weatherapp.utils.captureValues
import com.example.weatherapp.utils.getValueForTest
import com.example.weatherapp.weatherdetails.data.WeatherDetailsMapper
import com.example.weatherapp.weatherdetails.data.WeatherDetailsRepository
import com.example.weatherapp.weatherdetails.network.Loadable
import com.example.weatherapp.weatherdetails.network.model.WeatherDetailsModel
import com.example.weatherapp.weatherdetails.ui.WeatherDetailsViewModel
import com.example.weatherapp.weatherdetails.ui.WeatherViewState
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherDetailsViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherDetailsViewModel
    private val weatherDetailsRepository: WeatherDetailsRepository = mock()
    private val weatherDetailsMapper: WeatherDetailsMapper = mock()
    private val weatherViewState: WeatherViewState = mock()
    private val weatherDetailsModel: WeatherDetailsModel = mock()
    private val city = "London"

    @Before
    fun setup() {
        viewModel = WeatherDetailsViewModel(
            weatherDetailsRepository,
            weatherDetailsMapper
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch weather details  - then get weather details from repo`() =
        runBlockingTest {
            mockSuccessResponse()
            viewModel.fetchWeatherDetails(city)
            verify(weatherDetailsRepository, times(1)).fetchWeatherDetails(city)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch weather details successfully - then map the weatherModel to weatherViewState`() =
        runBlockingTest {
            mockSuccessResponse()
            viewModel.fetchWeatherDetails(city)
            verify(weatherDetailsMapper, times(1)).mapToWeatherViewState(weatherDetailsModel)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch weather details - then get all weather data of a city successfully`() =
        runBlockingTest {
            mockSuccessResponse()
            viewModel.fetchWeatherDetails(city)
            val actualWeatherViewState = viewModel.weatherViewStateLiveData.getValueForTest()

            Assert.assertEquals(weatherViewState, actualWeatherViewState)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `when start fetching weather details - then show loading`() = runBlockingTest {
        mockLoadingResponse()
        viewModel.loadingLiveData.captureValues {
            viewModel.fetchWeatherDetails(city)
            Assert.assertEquals(true, values[0])
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when start completing weather details - then hide loading`() = runBlockingTest {
        mockSuccessResponse()
        viewModel.loadingLiveData.captureValues {
            viewModel.fetchWeatherDetails(city)
            Assert.assertEquals(false, values.first())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch weather details and it has an error - then show error`() = runBlockingTest {
        mockErrorResponse()
        viewModel.fetchWeatherDetails(city)
        val actualResult = viewModel.hasErrorLiveData.getValueForTest()
        Assert.assertEquals(true, actualResult)

    }

    @ExperimentalCoroutinesApi
    private fun mockSuccessResponse() = runBlockingTest {
        whenever(weatherDetailsRepository.fetchWeatherDetails(city)).thenReturn(
            flow {
                emit(Loadable.Success(weatherDetailsModel))
            }
        )
        whenever(weatherDetailsMapper.mapToWeatherViewState(weatherDetailsModel)).thenReturn(weatherViewState)
    }

    @ExperimentalCoroutinesApi
    private fun mockLoadingResponse() = runBlockingTest {
        whenever(weatherDetailsRepository.fetchWeatherDetails(city)).thenReturn(
            flow {
                emit(Loadable.Loading)
            }
        )
    }

    @ExperimentalCoroutinesApi
    private fun mockErrorResponse() = runBlockingTest {
        whenever(weatherDetailsRepository.fetchWeatherDetails(city)).thenReturn(
            flow {
                emit(Loadable.Error)
            }
        )
    }

}