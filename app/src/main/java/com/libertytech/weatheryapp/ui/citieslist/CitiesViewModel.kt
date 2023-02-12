package com.libertytech.weatheryapp.ui.citieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.libertytech.core.data.local.model.CityEntity
import com.libertytech.core.domain.usecase.GetCitiesListUseCase
import com.libertytech.core.domain.usecase.StoreCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {

    class UseCases @Inject constructor(
        val storeCityUseCase: StoreCityUseCase,
        val getCitiesListUseCase: GetCitiesListUseCase
    )

    private val cityMState = MutableStateFlow<CityListState>(CityListState.Init)
    val cityState: StateFlow<CityListState> get() = cityMState

    fun getAllCities() {
        viewModelScope.launch {
            useCases.getCitiesListUseCase.invoke()
                .onStart {
                    cityMState.value = CityListState.IsLoading(isLoading = true)
                }
                .collect { cities ->
                    Log.d("ANNAS", "Got $cities")
                    cityMState.value = CityListState.IsLoading(isLoading = false)
                    if (cities.isEmpty()) {
                        cityMState.value = CityListState.EmptyList
                    } else {
                        cityMState.value = CityListState.SuccessCollect(cities)
                    }
                }
        }
    }

    fun saveCity(name: String, lat: Double, lng: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            useCases.storeCityUseCase.invoke(name, lat, lng)
        }
    }
}

sealed class CityListState {
    object Init : CityListState()
    data class IsLoading(val isLoading: Boolean) : CityListState()
    object EmptyList : CityListState()
    data class SuccessCollect(val cities: List<CityEntity>) : CityListState()
}