package info.moevm.se.domain.entities

data class Item (
    val name: String,
    val type: ItemTypes,
    val colors: ItemColors,
    val srcId: Int,
    val tempFrom: Int,
    val tempTo: Int,
    var selected: Boolean = false
)

