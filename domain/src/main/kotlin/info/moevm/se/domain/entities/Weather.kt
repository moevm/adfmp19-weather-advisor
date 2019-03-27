package info.moevm.se.domain.entities

import java.util.*


data class Weather(
    val date: Date = Date(),
    val tempUnits: TempUnits = TempUnits.CELSIUS,
    val state: WeatherStates,
    val temp: Int,
    val wind: Int,
    val wet: Int
)

enum class TempUnits {
    CELSIUS, FRIGATES
}

enum class WeatherStates {
    SUN, RAIN, CLOUDS, SNOW, STORM, FOG
}