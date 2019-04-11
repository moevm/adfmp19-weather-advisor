package info.moevm.se.data.repositories

import info.moevm.se.data.remote.entities.map
import info.moevm.se.data.remote.services.RestAPI
import info.moevm.se.domain.entities.Weather
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccuWeatherRepository @Inject constructor(
    val retrofit: Retrofit
) {
    fun loadWeatherForCity(code: String): Maybe<Weather> = retrofit.create(RestAPI::class.java)
        .getWeather(code)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { it[0].map() }

}