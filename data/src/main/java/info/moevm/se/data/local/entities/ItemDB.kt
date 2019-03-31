package info.moevm.se.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import info.moevm.se.data.local.converters.ColorConverter
import info.moevm.se.data.local.converters.TypeConverter
import info.moevm.se.domain.entities.Item

@Entity(tableName = "item")
data class ItemDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    var type: Int,
    var colors: Int
)


fun ItemDB.map() = Item(
    name = this.name,
    type = TypeConverter.to(this.type),
    colors = ColorConverter.to(this.colors)
)

fun Item.map() = ItemDB(
    id = 0,
    name = this.name,
    type = TypeConverter.from(this.type),
    colors = ColorConverter.from(this.colors)
)