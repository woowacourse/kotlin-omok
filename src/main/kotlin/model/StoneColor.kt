package model

enum class StoneColor {
    WHITE,
    BLACK,
    ;

    fun isSameColor(color: StoneColor): Boolean = this == color

    fun switch(): StoneColor =
        when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
        }
}
