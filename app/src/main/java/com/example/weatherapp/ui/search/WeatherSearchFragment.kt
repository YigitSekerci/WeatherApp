package com.example.weatherapp.ui.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherSearchBinding
import com.example.weatherapp.model.WeatherInfo
import com.example.weatherapp.util.exceptions.LocationServiceIsClosedException
import com.example.weatherapp.util.makeExceptionDialogue
import com.example.weatherapp.util.speedAbbreviation
import com.example.weatherapp.util.temperatureAbbreviation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherSearchFragment : Fragment() {
    private val viewModel: WeatherSearchViewModel by activityViewModels()
    private var _binding: FragmentWeatherSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherSearchBinding.inflate(inflater, container, false)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Weather Search"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivSearch.setOnClickListener {
            val searchLocation: String = binding.etxCity.text.toString()
            if (viewModel.needCurrentLocation(searchLocation)) {
                currentLocationQuery()
            } else {
                viewModel.searchSpecificLocation(searchLocation)
            }
        }

        viewModel.state.observe(viewLifecycleOwner) {
            if (it == null) {
                return@observe
            }

            when {
                it.isLoading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                it.errorMessage != null -> {
                    binding.progressBar.visibility = View.GONE
                    makeExceptionDialogue(it.errorMessage.toString())
                    viewModel.clearErrorMessage()
                }
                it.weatherInfo != null ->{
                    val weatherInfo: WeatherInfo = it.weatherInfo
                    bindViews(weatherInfo)
                }
                else -> {
                    //No need to anything
                }
            }
        }
    }

    private fun currentLocationQuery() {
        requestPermission()
        if (hasLocationPermission()) {
            tryToSearchCurrent()
        }else{
            makeExceptionDialogue("In order to make current location query please accept location permission.")
        }
    }

    private fun tryToSearchCurrent() {
        try {
            viewModel.searchCurrentLocation()
        } catch (e: LocationServiceIsClosedException) {
            makeExceptionDialogue("Location service is closed. Please open your GPS or INTERNET services.")
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            tryToSearchCurrent()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            1
        )
    }

    @SuppressLint("SetTextI18n")
    private fun bindViews(weatherInfo: WeatherInfo) {
        binding.apply {
            progressBar.visibility = View.GONE

            txCity.apply {
                text = weatherInfo.location.name
            }

            txObservationTime.apply {
                text = "Observation Time: ${weatherInfo.current.observation_time}"
            }

            txDescription.apply {
                text = weatherInfo.current.weather_descriptions[0]
            }

            txFeelslike.apply {
                text =
                    "Feelslike\n${weatherInfo.current.feelslike} ${temperatureAbbreviation(viewModel.currentUnit)}"
            }

            txTemperature.apply {
                text = "Temperature\n${weatherInfo.current.temperature} ${
                    temperatureAbbreviation(viewModel.currentUnit)
                }"
            }

            txWindDirection.apply {
                text = "Wind Direction\n${weatherInfo.current.wind_dir}"
            }

            txWindSpeed.apply {
                text =
                    "Wind Speed\n${weatherInfo.current.wind_speed} ${speedAbbreviation(viewModel.currentUnit)}"
            }

            Glide.with(this@WeatherSearchFragment)
                .load(weatherInfo.current.weather_icons[0])
                .placeholder(R.drawable.ic_placeholder)
                .fitCenter()
                .circleCrop()
                .into(binding.ivWeatherIcon)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}