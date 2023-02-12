import com.libertytech.core.data.network.model.TempResponse
import com.libertytech.core.data.network.model.WeatherApiResponse
import com.libertytech.core.data.network.model.WeatherResponse
import com.libertytech.core.data.network.model.WindResponse
import com.libertytech.core.data.repository.WeatherRepository
import com.libertytech.core.domain.model.Weather
import com.libertytech.core.domain.usecase.GetWeatherDetailsUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetWeatherDetailsUseCaseTest {

    @Mock
    lateinit var weatherRepository: WeatherRepository

    private lateinit var getWeatherDetailsUseCase: GetWeatherDetailsUseCase

    @Before
    fun setUp() {
        getWeatherDetailsUseCase = GetWeatherDetailsUseCase(weatherRepository)
    }

    @Test
    fun `Given WeatherApiResponse When mapping Then we get the right Weather`() {

        val weatherApiResponse = WeatherApiResponse(
            weather = listOf(WeatherResponse(description = "clear sky", icon = "01d")),
            temp = TempResponse(temp = 20.0, pressure = 1013, humidity = 70),
            wind = WindResponse(speed = 5.0, deg = 200)
        )

        val expectedWeather = Weather(
            description = "clear sky",
            icon = "01d",
            temperature = 20.0,
            pressure = 1013,
            humidity = 70,
            windSpeed = 5.0,
            windDirectionInDeg = 200
        )

        val actualWeather = getWeatherDetailsUseCase.mapWeatherResponse(weatherApiResponse)

        assertEquals(expectedWeather, actualWeather)
    }
}