package com.libertytech.weatheryapp.ui.citieslist

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
import androidx.navigation.fragment.findNavController
import com.libertytech.weatheryapp.R
import com.libertytech.weatheryapp.databinding.FragmentCitiesListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class CitiesListFragment : Fragment() {

    private var _binding: FragmentCitiesListBinding? = null
    private lateinit var citiesAdapter : CityListAdapter
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
        initUi()
        observeCitiesState()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllCities()
    }

    private fun observeCitiesState(){
        viewModel.cityState
            .flowWithLifecycle(lifecycle,  Lifecycle.State.STARTED)
            .onEach { citiesState -> handleCitiesState(citiesState) }
            .launchIn(lifecycleScope)
    }

    private fun handleCitiesState(citiesState: CityListState) = when(citiesState) {
        CityListState.IsLoading -> Toast.makeText(activity, "Chargement des villes", Toast.LENGTH_SHORT).show()
        CityListState.EmptyList -> binding.emptyTextview.visibility = View.VISIBLE
        CityListState.Init -> Unit
        is CityListState.SuccessCollect -> {
            binding.emptyTextview.visibility = View.GONE
            citiesAdapter.updateData(citiesState.cities)
        }
    }

    private fun initUi() {
        citiesAdapter = CityListAdapter {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.citiesRecyclerview.adapter = citiesAdapter
        binding.addCityButton.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}