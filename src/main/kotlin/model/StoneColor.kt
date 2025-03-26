package model

enum class StoneColor {
    WHITE,
    BLACK,
    ;

    fun switch(): StoneColor =
        when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
        }
}
