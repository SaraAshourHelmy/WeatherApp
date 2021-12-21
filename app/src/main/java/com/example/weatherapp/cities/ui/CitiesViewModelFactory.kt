package com.example.weatherapp.cities.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.cities.data.CitiesRepository
import javax.inject.Inject

class CitiesViewModelFactory @Inject constructor(val citiesRepository: CitiesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CitiesViewModel(citiesRepository) as T
    }
}