package com.example.weatherapp.weatherdetails.di

import com.example.weatherapp.weatherdetails.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://www.metaweather.com/"

@Module
@InstallIn(FragmentComponent::class)
class WeatherModule {

    @Provides
    fun getWeatherApi(retrofit: Retrofit) = retrofit.create(WeatherApi::class.java)

    @Provides
    fun getRetrofit() =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}