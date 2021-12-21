package com.example.weatherapp.weatherdetails.data

import com.example.weatherapp.R
import com.example.weatherapp.weatherdetails.network.model.WeatherDetailsModel
import com.example.weatherapp.weatherdetails.ui.WeatherViewState
import com.example.weatherapp.utils.StringProvider
import com.example.weatherapp.utils.getApproximateNumber
import com.example.weatherapp.weatherdetails.di.BASE_URL
import javax.inject.Inject

class WeatherDetailsMapper @Inject constructor(val stringProvider: StringProvider) {

    fun mapToWeatherViewState(model: WeatherDetailsModel): WeatherViewState {
        return WeatherViewState(
            temp = getFormatTemp(model.temp),
            minMaxTemp = getFormatMinMaxTemp(model.minTemp, model.maxTemp),
            weatherStateName = model.weatherStateName,
            date = model.applicableDate,
            windSpeed = getFormatWindSpeed(model.windSpeed),
            pressure = getFormatPressure(model.airPressure),
            humidity = getFormatHumidity(model.humidity),
            visibility = getFormatVisibility(model.visibility),
            predictability = getFormatPredictability(model.predictability),
            weatherIcon = getWeatherIconUrl(model.weatherStateAbbr)
        )
    }

    fun getFormatTemp(temp: Double): String {
        return String.format(
            stringProvider.getString(R.string.temp),
            getApproximateNumber(temp)
        )
    }

    fun getFormatMinMaxTemp(min: Double, max: Double): String {
        return String.format(
            stringProvider.getString(R.string.minMaxTemp),
            getApproximateNumber(min),
            getApproximateNumber(max)
        )
    }

    fun getFormatWindSpeed(speed: Double): String {
        return String.format(
            stringProvider.getString(R.string.windSpeed),
            getApproximateNumber(speed)
        )
    }

    fun getFormatPressure(pressure: Double): String {
        return String.format(
            stringProvider.getString(R.string.pressure),
            getApproximateNumber(pressure)
        )
    }

    fun getFormatHumidity(humidity: Int): String {
        return String.format(
            stringProvider.getString(R.string.precent),
            humidity
        )
    }

    fun getFormatVisibility(visibility: Double): String {
        return String.format(
            stringProvider.getString(R.string.visibility),
            getApproximateNumber(visibility)
        )
    }

    fun getFormatPredictability(predictability: Int): String {
        return String.format(
            stringProvider.getString(R.string.precent),
            predictability
        )
    }

    fun getWeatherIconUrl(weatherAbbr: String): String {
        val imagePath = "static/img/weather/png/"
        return "$BASE_URL$imagePath$weatherAbbr.png"
    }
}
