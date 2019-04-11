package info.moevm.se.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import info.moevm.se.data.local.dao.ItemDAO
import info.moevm.se.data.local.dao.LocationDAO
import info.moevm.se.data.local.entities.ItemDB
import info.moevm.se.data.local.entities.LocationDB

@Database(entities = [ItemDB::class, LocationDB::class], version = 1)
abstract class WeatherAdviserDB : RoomDatabase() {
    abstract fun itemDAO(): ItemDAO

    abstract fun locationDAO(): LocationDAO
}