package info.moevm.se.data.remote.services

import info.moevm.se.data.remote.entities.City
import info.moevm.se.data.remote.entities.Country
import info.moevm.se.data.remote.entities.Location
import info.moevm.se.data.remote.entities.Weather
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path

interface RestAPI {

    @GET("locations/v1/countries/{region}?apikey=$ACCU_WEATHER_KEY")
    fun getCountries(@Path("region") region: String): Maybe<List<Country>>

    @GET("locations/v1/adminareas/{country}?apikey=$ACCU_WEATHER_KEY")
    fun getCities(@Path("country") country: String): Maybe<List<City>>

    @GET("locations/v1/cities/{country}/{administrative}/search??apikey=$ACCU_WEATHER_KEY&q={city}")
    fun getLocation(
        @Path("country") country: String,
        @Path("administrative") administrative: String,
        @Path("city") city: String
    ): Maybe<List<Location>>

    @GET("currentconditions/v1/{code}?apikey=$ACCU_WEATHER_KEY&details=true")
    fun getWeather(@Path("code") code: String): Maybe<List<Weather>>

    companion object {
        const val ACCU_WEATHER_KEY = "zbKYnGAApvxDfoTkHT3gKWXEwcPtNvQP"
    }
}