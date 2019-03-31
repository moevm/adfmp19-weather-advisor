package info.moevm.se.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import info.moevm.se.data.local.dao.ItemDAO
import info.moevm.se.data.local.entities.ItemDB

@Database(entities = [ItemDB::class], version = 1)
abstract class WeatherAdviserDB : RoomDatabase() {
    abstract fun itemDAO(): ItemDAO
}