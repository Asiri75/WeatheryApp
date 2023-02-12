package com.libertytech.weatheryapp.ui.cityweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.libertytech.core.domain.model.Weather
import com.libertytech.core.domain.usecase.GetWeatherDetailsUseCase
import com.libertytech.weatheryapp.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {

    class UseCases @Inject constructor(
        val getWeatherDetailsUseCase: GetWeatherDetailsUseCase
    )

    private val weatherMState = MutableStateFlow<WeatherState>(WeatherState.Init)
    val weatherState: StateFlow<WeatherState> get() = weatherMState

    fun getWeatherFor(lat: Double, lng: Double) {
        viewModelScope.launch {
            useCases.getWeatherDetailsUseCase.invoke(lat, lng)
                .onStart {
                    weatherMState.value = WeatherState.IsLoading(isLoading = true)
                }
                .catch { error ->
                    weatherMState.value = WeatherState.IsLoading(isLoading = false)
                    weatherMState.value = WeatherState.Error(message = error.message)
                }
                .collect { weatherState ->
                    weatherMState.value = WeatherState.IsLoading(isLoading = false)
                    weatherMState.value = WeatherState.SuccessCollect(weatherState)
                }
        }
    }
}

sealed class WeatherState {
    object Init : WeatherState()
    data class IsLoading(val isLoading: Boolean) : WeatherState()
    data class Error(val message: String?) : WeatherState()
    data class SuccessCollect(val weather: Weather) : WeatherState()
}