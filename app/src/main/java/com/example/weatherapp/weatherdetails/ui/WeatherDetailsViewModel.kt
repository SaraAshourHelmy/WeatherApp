package com.example.weatherapp.weatherdetails.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.weatherdetails.data.WeatherDetailsMapper
import com.example.weatherapp.weatherdetails.data.WeatherDetailsRepository
import com.example.weatherapp.weatherdetails.network.Loadable
import com.example.weatherapp.weatherdetails.network.model.WeatherDetailsModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WeatherDetailsViewModel(
    private val weatherDetailsRepository: WeatherDetailsRepository,
    private val weatherDetailsMapper: WeatherDetailsMapper
) : ViewModel() {

    private var _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    private var _hasErrorLiveData = MutableLiveData<Boolean>(false)
    val hasErrorLiveData: LiveData<Boolean>
        get() = _hasErrorLiveData

    private var _weatherViewStateLiveData = MutableLiveData<WeatherViewState>()
    val weatherViewStateLiveData: LiveData<WeatherViewState>
        get() = _weatherViewStateLiveData

    fun fetchWeatherDetails(city: String) {
        viewModelScope.launch {
            weatherDetailsRepository.fetchWeatherDetails(city)
                .onEach { result ->
                    if (result !is Loadable.Loading)
                        hideLoading()
                }
                .collect { response ->
                    getWeatherResponse(response)
                }
        }
    }

    private fun getWeatherResponse(loadable: Loadable<WeatherDetailsModel>) {
        when (loadable) {
            is Loadable.Loading -> showLoading()
            is Loadable.Success -> mapModelToWeatherViewState(loadable.data)
            is Loadable.Error -> showError()
        }
    }

    private fun mapModelToWeatherViewState(detailsModel: WeatherDetailsModel) {
        _weatherViewStateLiveData.value = weatherDetailsMapper.mapToWeatherViewState(detailsModel)
    }

    private fun showLoading() {
        _loadingLiveData.value = true
    }

    private fun hideLoading() {
        _loadingLiveData.value = false
    }

    private fun showError() {
        _hasErrorLiveData.value = true
    }
}
