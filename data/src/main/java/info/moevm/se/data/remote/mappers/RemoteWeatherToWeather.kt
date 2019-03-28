package info.moevm.se.data.remote.mappers

import info.moevm.se.data.remote.entities.Weather
import info.moevm.se.domain.repositories.OneDirectionMapper

class RemoteWeatherToWeather : OneDirectionMapper<Weather, info.moevm.se.domain.entities.Weather> {
    override fun map(value: Weather) = info.moevm.se.domain.entities.Weather(
        name = value.state,
        temp = value.temp.metric.value,
        wind = value.wind.speed.metric.value,
        windDirection = value.wind.direction.angle,
        wet = value.pressure.metric.value
    )
}