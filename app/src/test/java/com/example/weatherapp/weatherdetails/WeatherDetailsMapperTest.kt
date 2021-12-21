package com.example.weatherapp.weatherdetails

import com.example.weatherapp.R
import com.example.weatherapp.utils.StringProvider
import com.example.weatherapp.weatherdetails.data.WeatherDetailsMapper
import com.example.weatherapp.weatherdetails.network.model.WeatherDetailsModel
import com.example.weatherapp.weatherdetails.ui.WeatherViewState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Test

class WeatherDetailsMapperTest {

    private val stringProvider: StringProvider = mock()
    private val mapper = WeatherDetailsMapper(stringProvider)

    @Test
    fun `when format temp - then get formatted temp successfully`() {
        val format = "%s°C"
        whenever(stringProvider.getString(R.string.temp)).thenReturn(format)
        val actualResult = mapper.getFormatTemp(12.36)
        val expectedResult = String.format(format, 12.4)
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `when format min and max temp - then get formatted minMax temp successfully`() {
        val format = "%s - %s °C"
        whenever(stringProvider.getString(R.string.minMaxTemp)).thenReturn(format)
        val actualResult = mapper.getFormatMinMaxTemp(-1.45, 5.67)
        val expectedResult = String.format(format, -1.5, 5.7)
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `when format wind speed - then get formatted wind speed successfully`() {
        val format = "%s km/h"
        whenever(stringProvider.getString(R.string.windSpeed)).thenReturn(format)
        val actualResult = mapper.getFormatWindSpeed(2.66546)
        val expectedResult = String.format(format, 2.7)
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `when format pressure - then get formatted pressure successfully`() {
        val format = "%s mbar"
        whenever(stringProvider.getString(R.string.pressure)).thenReturn(format)
        val actualResult = mapper.getFormatPressure(1038.5)
        val expectedResult = String.format(format, 1038.5)
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `when format humidity - then get formatted humidity successfully`() {
        val format = "%s%%"
        whenever(stringProvider.getString(R.string.precent)).thenReturn(format)
        val actualResult = mapper.getFormatHumidity(85)
        val expectedResult = String.format(format, 85)
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `when format visibility - then get formatted visibility successfully`() {
        val format = "%s km"
        whenever(stringProvider.getString(R.string.visibility)).thenReturn(format)
        val actualResult = mapper.getFormatVisibility(7.46)
        val expectedResult = String.format(format, 7.5)
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `when format predictability - then get formatted predictability successfully`() {
        val format = "%s%%"
        whenever(stringProvider.getString(R.string.precent)).thenReturn(format)
        val actualResult = mapper.getFormatPredictability(70)
        val expectedResult = String.format(format, 70)
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `when receive weather abbreviation - then get the relevant weather icon url`() {
        val abbr = "hc"
        val actualUrl = mapper.getWeatherIconUrl(abbr)
        val expectedUrl = "https://www.metaweather.com/static/img/weather/png/$abbr.png"
        Assert.assertEquals(expectedUrl, actualUrl)
    }

    @Test
    fun `when receive weatherDetailsModel to be mapped -  then return weatherViewState successfully`() {
        mockStrings()
        val weatherDetailsModel = getWeatherDetailsModel()
        val actualWeatherViewState = mapper.mapToWeatherViewState(weatherDetailsModel)
        val expectedWeatherViewState = WeatherViewState(
            temp = mapper.getFormatTemp(11.690000000000001),
            minMaxTemp = mapper.getFormatMinMaxTemp(6.41, 10.885),
            weatherStateName = "Heavy Cloud",
            date = "2021-12-16",
            windSpeed = mapper.getFormatWindSpeed(2.6654683511095203),
            pressure = mapper.getFormatPressure(1038.5),
            humidity = mapper.getFormatHumidity(79),
            visibility = mapper.getFormatVisibility(7.466085560327686),
            predictability = mapper.getFormatPredictability(71),
            weatherIcon = mapper.getWeatherIconUrl(weatherDetailsModel.weatherStateAbbr)
        )

        Assert.assertEquals(expectedWeatherViewState, actualWeatherViewState)
    }

    private fun getWeatherDetailsModel(): WeatherDetailsModel {
        return WeatherDetailsModel(
            id = 6542245927845888,
            weatherStateName = "Heavy Cloud",
            weatherStateAbbr = "hc",
            windDirectionCompass = "WNW",
            createdDate = "2021-12-16T18:59:02.646367Z",
            applicableDate = "2021-12-16",
            minTemp = 6.41,
            maxTemp = 10.885,
            temp = 11.690000000000001,
            windSpeed = 2.6654683511095203,
            windDirection = 290.51503507237885,
            airPressure = 1038.5,
            humidity = 79,
            visibility = 7.466085560327686,
            predictability = 71
        )
    }

    private fun mockStrings() {
        whenever(stringProvider.getString(R.string.temp)).thenReturn("%s°C")
        whenever(stringProvider.getString(R.string.minMaxTemp)).thenReturn("%s - %s °C")
        whenever(stringProvider.getString(R.string.windSpeed)).thenReturn("%s km/h")
        whenever(stringProvider.getString(R.string.pressure)).thenReturn("%s mbar")
        whenever(stringProvider.getString(R.string.precent)).thenReturn("%s%%")
        whenever(stringProvider.getString(R.string.visibility)).thenReturn("%s km")
        whenever(stringProvider.getString(R.string.precent)).thenReturn("%s%%")
    }
}