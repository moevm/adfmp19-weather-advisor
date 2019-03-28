package info.moevm.se.weatheradvisor

import android.app.Application
import info.moevm.se.weatheradvisor.di.AppComponent
import info.moevm.se.weatheradvisor.di.AppModule
import info.moevm.se.weatheradvisor.di.DaggerAppComponent
import info.moevm.se.weatheradvisor.di.NetModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(AppModule(this))
            .network(NetModule("http://dataservice.accuweather.com/"))
            .build()
    }
}