package com.example.weatherapp.cities.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.cities.data.CitiesRepository

class CitiesViewModel(private val citiesRepository: CitiesRepository) : ViewModel() {

    private var _citiesLiveData = MutableLiveData<List<String>>()
    val citiesLiveData: LiveData<List<String>>
        get() = _citiesLiveData

    init {
        fetchCities()
    }

    fun fetchCities() {
        _citiesLiveData.value = citiesRepository.fetchCities()
    }

}
