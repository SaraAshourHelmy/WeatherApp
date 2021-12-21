package com.example.weatherapp.cities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.cities.data.CitiesRepository
import com.example.weatherapp.cities.ui.CitiesViewModel
import com.example.weatherapp.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CitiesViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val citiesRepository: CitiesRepository = mock()
    private val cityListResult: List<String> = mock()
    private lateinit var citiesViewModel: CitiesViewModel

    @Before
    fun setup() {
        citiesViewModel = CitiesViewModel(citiesRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch cities - then get cities from repository`() {
        mockFetchCityList()
        verify(citiesRepository, times(1)).fetchCities()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `when fetch cities - then get the city list successfully`() {
        mockFetchCityList()
        citiesViewModel.fetchCities()
        val actualCities = citiesViewModel.citiesLiveData.getValueForTest()

        Assert.assertEquals(cityListResult, actualCities)
    }

    private fun mockFetchCityList() {
        whenever(citiesRepository.fetchCities()).thenReturn(cityListResult)
    }


}