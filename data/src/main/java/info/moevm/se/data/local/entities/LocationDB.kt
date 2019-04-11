package info.moevm.se.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import info.moevm.se.domain.entities.Location

@Entity(tableName = "locations")
data class LocationDB(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val code: Int
)

fun LocationDB.map() = Location(id, name, code)

fun Location.map() = LocationDB(id, name, code)