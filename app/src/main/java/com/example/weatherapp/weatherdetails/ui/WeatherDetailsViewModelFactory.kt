package com.example.weatherapp.weatherdetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.weatherdetails.data.WeatherDetailsMapper
import com.example.weatherapp.weatherdetails.data.WeatherDetailsRepository
import javax.inject.Inject

class WeatherDetailsViewModelFactory @Inject constructor(
    private val weatherDetailsRepository: WeatherDetailsRepository,
    private val weatherDetailsMapper: WeatherDetailsMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherDetailsViewModel(
            weatherDetailsRepository,
            weatherDetailsMapper
        ) as T
    }
}