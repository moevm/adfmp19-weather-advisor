package info.moevm.se.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import info.moevm.se.data.local.entities.ItemDB
import io.reactivex.Maybe

@Dao
interface ItemDAO {

    @Query("SELECT * FROM item")
    fun all(): Maybe<List<ItemDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(value: ItemDB)

    @Update
    fun update(value: ItemDB)

    @Delete
    fun delete(value: ItemDB)

    @Query("SELECT * FROM item WHERE id = :id")
    fun itemById(id: String): Maybe<ItemDB>

}