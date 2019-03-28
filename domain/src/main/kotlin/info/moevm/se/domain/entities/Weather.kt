package info.moevm.se.domain.entities

import info.moevm.se.domain.entities.WeatherStates.CLOUDS
import java.util.*


data class Weather(
    val date: Date = Date(),
    val tempUnits: TempUnits = TempUnits.CELSIUS,
    val state: WeatherStates = CLOUDS,
    val name: String,
    val temp: Float,
    val wind: Float,
    val windDirection: Float,
    val wet: Float
)

enum class TempUnits {
    CELSIUS, FRIGATES
}

enum class WeatherStates {
    SUN, RAIN, CLOUDS, SNOW, STORM, FOG
}