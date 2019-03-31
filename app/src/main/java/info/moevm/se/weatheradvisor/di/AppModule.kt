package info.moevm.se.weatheradvisor.di

import dagger.Module
import android.app.Application
import javax.inject.Singleton
import dagger.Provides


@Module
class AppModule(val application: Application) {

    @Provides
    @Singleton
    fun provideApplication() = application

    @Provides
    @Singleton
    fun provideContext(application: Application) = application.applicationContext
}