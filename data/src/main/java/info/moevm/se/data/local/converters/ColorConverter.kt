package info.moevm.se.data.local.converters

import androidx.room.TypeConverter
import info.moevm.se.domain.entities.ItemColors

object ColorConverter {
    @TypeConverter
    fun from(property: ItemColors): Int = property.code

    @TypeConverter
    fun to(code: Int): ItemColors = when (code) {
        0 -> ItemColors.COLOR1
        1 -> ItemColors.COLOR2
        2 -> ItemColors.COLOR3
        3 -> ItemColors.COLOR4
        4 -> ItemColors.COLOR5
        5 -> ItemColors.COLOR6
        6 -> ItemColors.COLOR7
        7 -> ItemColors.COLOR8
        8 -> ItemColors.COLOR9
        9 -> ItemColors.COLOR10
        10 -> ItemColors.COLOR11
        11 -> ItemColors.COLOR12
        else -> throw IllegalArgumentException("Unknown color")
    }

}