package info.moevm.se.weatheradvisor.di

import dagger.Component
import info.moevm.se.data.local.db.DatabaseModule
import info.moevm.se.data.local.db.DatabaseModule_ProvideDatabaseFactory
import info.moevm.se.weatheradvisor.clotheeditorscreen.ClotheEditorActivity
import info.moevm.se.weatheradvisor.mainscreen.MainScreenActivity
import info.moevm.se.weatheradvisor.wardrobescreen.WardrobeActivity
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        DatabaseModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(activity: MainScreenActivity)

    fun inject(activity: ClotheEditorActivity)

    fun inject(activity: WardrobeActivity)

    @Component.Builder
    interface Builder {
        fun application(appModule: AppModule): Builder
        fun network(netModule: NetModule): Builder
        fun database(databaseModule: DatabaseModule): Builder
        fun build(): AppComponent
    }
}