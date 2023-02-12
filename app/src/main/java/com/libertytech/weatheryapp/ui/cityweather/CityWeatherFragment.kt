package com.libertytech.weatheryapp.ui.cityweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.libertytech.core.domain.model.Weather
import com.libertytech.weatheryapp.R
import com.libertytech.weatheryapp.databinding.FragmentCityWeatherBinding
import com.libertytech.weatheryapp.model.City
import com.libertytech.weatheryapp.ui.citieslist.CityListState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class CityWeatherFragment : Fragment() {

    private var _binding: FragmentCityWeatherBinding? = null
    private val viewModel by viewModels<CityWeatherViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeWeatherState()
        (arguments?.getSerializable("city") as? City)?.let { city ->
            binding.cityNameTextview.text = city.name
            viewModel.getWeatherFor(city.lat, city.lng)
        }
    }

    private fun observeWeatherState() {
        viewModel.weatherState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { weatherState -> handleWeatherState(weatherState) }
            .launchIn(lifecycleScope)
    }

    private fun handleWeatherState(weatherState: WeatherState) = when (weatherState) {
        is WeatherState.IsLoading -> binding.loadingProgress.visibility = if (weatherState.isLoading) View.VISIBLE else View.GONE
        WeatherState.Init -> Unit
        is WeatherState.Error -> Toast.makeText(requireActivity(), weatherState.message, Toast.LENGTH_LONG).show()
        is WeatherState.SuccessCollect -> {
            updateUi(weatherState.weather)
        }
    }

    private fun updateUi(weather: Weather) {
        binding.weatherDescriptionTextview.text = weather.description
        binding.weatherWindTextview.text = getString(R.string.wind, weather.windSpeed.toInt().toString())
        binding.tempTextview.text = getString(R.string.temp, weather.temperature.toInt().toString())
        binding.weatherImageview.setImageResource(resources.getIdentifier("ic_${weather.icon}","drawable", activity?.packageName))
        binding.weatherWindImageview.rotation = weather.windDirectionInDeg.toFloat()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}