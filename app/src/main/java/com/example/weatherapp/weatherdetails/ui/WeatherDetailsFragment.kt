package com.example.weatherapp.weatherdetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherDetailsFragment : Fragment() {
    private lateinit var binding: FragmentWeatherDetailsBinding
    private lateinit var viewModel: WeatherDetailsViewModel
    private val args: WeatherDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: WeatherDetailsViewModelFactory
    

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewModel()
        observeLoading()
        observeError()
        observeWeatherDetails()
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(WeatherDetailsViewModel::class.java)
        viewModel.fetchWeatherDetails(args.cityName)
    }

    private fun observeLoading() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingWeatherDetails.visibility = when (isLoading) {
                true -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    private fun observeError() {
        viewModel.hasErrorLiveData.observe(viewLifecycleOwner) { hasError ->
            if (hasError)
                Snackbar.make(binding.root, getString(R.string.errorMessage), Snackbar.LENGTH_LONG)
                    .show()
        }
    }

    private fun observeWeatherDetails() {
        viewModel.weatherViewStateLiveData.observe(viewLifecycleOwner) { weatherModel ->
            showWeatherDetails(weatherModel)
        }
    }

    private fun showWeatherDetails(weatherViewState: WeatherViewState) {
        binding.weatherDetailsCard.visibility = View.VISIBLE
        binding.cityNameValue.text = args.cityName
        binding.tempValue.text = weatherViewState.temp
        binding.weatherStateName.text = weatherViewState.weatherStateName
        binding.dateValue.text = weatherViewState.date
        binding.windSpeedValue.text = weatherViewState.windSpeed
        binding.pressureValue.text = weatherViewState.pressure
        binding.humidityValue.text = weatherViewState.humidity
        binding.visibilityValue.text = weatherViewState.visibility
        binding.predictabilityValue.text = weatherViewState.predictability
        binding.minMaxTemp.text = weatherViewState.minMaxTemp
        Picasso.get().load(weatherViewState.weatherIcon).into(binding.weatherImage)
    }
}