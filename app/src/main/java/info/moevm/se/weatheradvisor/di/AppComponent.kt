package info.moevm.se.weatheradvisor.di

import dagger.Component
import info.moevm.se.weatheradvisor.mainscreen.MainScreenActivity
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        NetModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(activity: MainScreenActivity)

    @Component.Builder
    interface Builder {
        fun application(appModule: AppModule): Builder
        fun network(netModule: NetModule): Builder
        fun build(): AppComponent
    }
}