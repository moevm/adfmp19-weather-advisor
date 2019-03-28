package info.moevm.se.weatheradvisor.di

import dagger.Module
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import javax.inject.Singleton
import dagger.Provides
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import android.app.Application
import info.moevm.se.data.repositories.AccuWeatherRepository
import okhttp3.Cache


@Module
class NetModule(var baseUrl: String) {

    @Provides
    @Singleton
    fun provideHttpCache(application: Application) = Cache(application.cacheDir, 15 * 1024 * 1024)

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache) = OkHttpClient.Builder()
        .followRedirects(false)
        .cache(cache)
        .build()

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideAccuWeatherRepository(retrofit: Retrofit) = AccuWeatherRepository(retrofit)

}