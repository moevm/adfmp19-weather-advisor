package info.moevm.se.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import info.moevm.se.data.local.entities.LocationDB
import io.reactivex.Maybe

@Dao
interface LocationDAO {
    @Query("SELECT * FROM locations")
    fun all(): Maybe<List<LocationDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(value: LocationDB)

    @Update
    fun update(value: LocationDB)

    @Delete
    fun delete(value: LocationDB)

    @Query("SELECT * FROM locations WHERE id=:id")
    fun itemById(id: String): Maybe<LocationDB>

    @Query("SELECT * FROM locations WHERE name LIKE :prefix COLLATE NOCASE")
    fun itemByPrefix(prefix: String): Maybe<LocationDB>


}