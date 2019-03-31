package info.moevm.se.data.local.converters

import androidx.room.TypeConverter
import info.moevm.se.domain.entities.ItemTypes
import java.lang.IllegalArgumentException

object TypeConverter {
    @TypeConverter
    fun from(property: ItemTypes): Int = property.code

    @TypeConverter
    fun to(code: Int): ItemTypes = when (code) {
        0 -> ItemTypes.HEAD
        1 -> ItemTypes.OVERBODY
        2 -> ItemTypes.BODY
        3 -> ItemTypes.LEGS
        4 -> ItemTypes.FEET
        else -> throw IllegalArgumentException("Unknown type")
    }
}