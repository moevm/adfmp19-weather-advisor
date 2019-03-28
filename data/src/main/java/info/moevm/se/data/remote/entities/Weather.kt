package info.moevm.se.data.remote.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("WeatherText")
    @Expose
    val state: String,

    @SerializedName("Temperature")
    @Expose
    val temp: Temperature,

    @SerializedName("Wind")
    @Expose
    val wind: Wind,

    @SerializedName("Pressure")
    @Expose
    val pressure: Pressure
)

data class Temperature(
    @SerializedName("Metric")
    @Expose
    val metric: Metric
)

data class Wind(
    @SerializedName("Direction")
    @Expose
    val direction: Direction,

    @SerializedName("Speed")
    @Expose
    val speed: Speed
)

data class Pressure(
    @SerializedName("Metric")
    @Expose
    val metric: Metric
)

data class Metric(
    @SerializedName("Value")
    @Expose
    val value: Float,

    @SerializedName("Unit")
    @Expose
    val unit: String
)

data class Direction(
    @SerializedName("Degrees")
    @Expose
    val angle: Float
)

data class Speed(
    @SerializedName("Metric")
    @Expose
    val metric: Metric
)

fun Weather.map() = info.moevm.se.domain.entities.Weather(
    name = this.state,
    temp = this.temp.metric.value,
    wind = this.wind.speed.metric.value,
    windDirection = this.wind.direction.angle,
    wet = this.pressure.metric.value
)


