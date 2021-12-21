package com.example.weatherapp.cities.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weatherapp.databinding.FragmentCitiesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CitiesFragment : Fragment() {
    private lateinit var binding: FragmentCitiesBinding
    private lateinit var viewModel: CitiesViewModel

    @Inject
    lateinit var viewModelFactory: CitiesViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupCityList()
        setupViewModel()
        observeLiveData()
    }

    private fun setupCityList() {
        val layoutManager = GridLayoutManager(context, 2)
        binding.cityList.layoutManager = layoutManager

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(CitiesViewModel::class.java)
    }

    private fun observeLiveData() {
        viewModel.citiesLiveData.observe(viewLifecycleOwner) { cities ->
            binding.cityList.adapter = CityAdapter(cities)
        }
    }
}