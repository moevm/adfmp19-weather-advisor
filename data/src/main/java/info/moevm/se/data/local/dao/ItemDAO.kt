package info.moevm.se.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
import info.moevm.se.data.local.converters.ColorConverter
import info.moevm.se.data.local.converters.TypeConverter
import info.moevm.se.data.local.entities.ItemDB
import io.reactivex.Maybe

@Dao
interface ItemDAO {

    @Query("SELECT * FROM item")
    fun all(): Maybe<List<ItemDB>>

    @Insert
    fun insert(value: ItemDB)

    @Update
    fun update(value: ItemDB)

    @Delete
    fun delete(value: ItemDB)

    fun itemById(id: String): Maybe<ItemDB>

}