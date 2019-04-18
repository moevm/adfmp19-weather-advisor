package info.moevm.se.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import info.moevm.se.data.local.converters.ColorConverter
import info.moevm.se.data.local.converters.TypeConverter
import info.moevm.se.domain.entities.Item

@Entity(tableName = "item")
data class ItemDB(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    var type: Int,
    var colors: Int,
    var srcId: Int,
    val fromTemp: Int,
    val toTemp: Int,
    val selected: Boolean
)


fun ItemDB.map() = Item(
    id = this.id,
    name = this.name,
    type = TypeConverter.to(this.type),
    colors = ColorConverter.to(this.colors),
    srcId = this.srcId,
    tempFrom = this.fromTemp,
    tempTo = this.toTemp,
    selected = this.selected
)

fun Item.map() = ItemDB(
    id = this.id,
    name = this.name,
    type = TypeConverter.from(this.type),
    colors = ColorConverter.from(this.colors),
    srcId = this.srcId,
    fromTemp = this.tempFrom,
    toTemp = this.tempTo,
    selected = this.selected
)