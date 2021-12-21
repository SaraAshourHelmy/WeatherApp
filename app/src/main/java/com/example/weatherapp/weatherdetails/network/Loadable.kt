package com.example.weatherapp.weatherdetails.network

sealed class Loadable<out R> {
    data class Success<out T>(val data: T) : Loadable<T>()
    object Error : Loadable<Nothing>()
    object Loading : Loadable<Nothing>()
}

