package com.example.weatherapp.cities.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.CityListItemBinding

class CityAdapter(private val cities: List<String>) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CityListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount() = cities.size


    inner class ViewHolder(private val binding: CityListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cityName: String) {
            binding.cityName.text = cityName
            binding.root.setOnClickListener {
                it.findNavController()
                    .navigate(CitiesFragmentDirections.actionCitiesDestToDetailsDest(cityName))
            }
        }
    }
}

