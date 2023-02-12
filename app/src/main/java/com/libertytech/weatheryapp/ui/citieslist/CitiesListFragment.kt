package com.libertytech.weatheryapp.ui.citieslist

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.libertytech.weatheryapp.BuildConfig
import com.libertytech.weatheryapp.R
import com.libertytech.weatheryapp.databinding.FragmentCitiesListBinding
import com.libertytech.weatheryapp.model.City
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class CitiesListFragment : Fragment() {

    private var _binding: FragmentCitiesListBinding? = null
    private lateinit var citiesAdapter: CityListAdapter
    private val viewModel by viewModels<CitiesViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!Places.isInitialized()) {
            Places.initialize(requireActivity(), BuildConfig.GOOGLE_API_KEY, Locale.FRANCE)
        }
        initUi()
        observeCitiesState()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllCities()
    }

    private val citySearchResultListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                Autocomplete.getPlaceFromIntent(it).let { place ->
                    place.toCity()?.let { city ->
                        viewModel.saveCity(city)
                        goToCityWeather(city)
                    }
                }
            }
        }
    }

    private fun goToCityWeather(city: City) {
        val bundle = bundleOf("city" to city)
        findNavController().navigate(R.id.action_CitiesListFragment_to_CityWeatherFragment, bundle)
    }

    private fun observeCitiesState() {
        viewModel.cityState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { citiesState -> handleCitiesState(citiesState) }
            .launchIn(lifecycleScope)
    }

    private fun handleCitiesState(citiesState: CityListState) = when (citiesState) {
        is CityListState.IsLoading -> binding.loadingProgress.visibility = if (citiesState.isLoading) View.VISIBLE else View.GONE
        CityListState.EmptyList -> binding.emptyTextview.visibility = View.VISIBLE
        CityListState.Init -> Unit
        is CityListState.SuccessCollect -> {
            binding.emptyTextview.visibility = View.GONE
            citiesAdapter.updateData(citiesState.cities)
        }
    }

    private fun initUi() {
        citiesAdapter = CityListAdapter { citySelected ->
            goToCityWeather(citySelected)
        }
        binding.citiesRecyclerview.adapter = citiesAdapter
        binding.addCityButton.setOnClickListener {
            launchCitySearch()
        }
    }

    private fun launchCitySearch() {
        val fields = listOf(Place.Field.NAME, Place.Field.LAT_LNG)
        activity?.let {
            citySearchResultListener.launch(
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(it)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun Place.toCity(): City? =
    if (this.name != null && this.latLng?.latitude != null && this.latLng?.longitude != null) {
        City(
            name = this.name,
            lat = this.latLng.latitude,
            lng = this.latLng.longitude
        )
    } else null

