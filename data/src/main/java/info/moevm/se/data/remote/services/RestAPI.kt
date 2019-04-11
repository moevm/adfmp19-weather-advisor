package info.moevm.se.data.remote.services

import info.moevm.se.data.remote.entities.CityRM
import info.moevm.se.data.remote.entities.CountryRM
import info.moevm.se.data.remote.entities.FullLocationRM
import info.moevm.se.data.remote.entities.LocationRM
import info.moevm.se.data.remote.entities.WeatherRM
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestAPI {

    @GET("locations/v1/countries/{region}?apikey=$ACCU_WEATHER_KEY")
    fun getCountries(@Path("region") region: String): Maybe<List<CountryRM>>

    @GET("locations/v1/adminareas/{country}?apikey=$ACCU_WEATHER_KEY")
    fun getCities(@Path("country") country: String): Maybe<List<CityRM>>

    @GET("locations/v1/cities/{country}/{administrative}/search?apikey=$ACCU_WEATHER_KEY&details=true&")
    fun getLocation(
        @Path("country") country: String,
        @Path("administrative") administrative: String,
        @Query("q") city: String
    ): Maybe<List<LocationRM>>

    @GET("locations/v1/cities/autocomplete?apikey=$ACCU_WEATHER_KEY")
    fun getLocationAuto(@Query("q") city: String): Maybe<List<FullLocationRM>>

    @GET("currentconditions/v1/{code}?apikey=$ACCU_WEATHER_KEY&details=true")
    fun getWeather(@Path("code") code: String): Maybe<List<WeatherRM>>

    companion object {
        const val ACCU_WEATHER_KEY = "zbKYnGAApvxDfoTkHT3gKWXEwcPtNvQP"
    }
}