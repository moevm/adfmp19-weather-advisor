package info.moevm.se.domain.entities

enum class ItemTypes(override val code: Int) : IntProperty {
    HEAD(0),
    OVERBODY(1),
    BODY(2),
    LEGS(3),
    FEET(4);
}