package info.moevm.se.data.local.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): WeatherAdviserDB =
        Room.databaseBuilder(context, WeatherAdviserDB::class.java, "weather_adviser").build()

    @Provides
    @Singleton
    fun provideItemDAO(database: WeatherAdviserDB) = database.itemDAO()

    @Provides
    @Singleton
    fun provideLovationDAO(database: WeatherAdviserDB) = database.locationDAO()
}
