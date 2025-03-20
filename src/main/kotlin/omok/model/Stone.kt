package omok.model

import rule.wrapper.point.Point

data class Stone(
    val point: Point,
    val color: StoneColor,
)

enum class StoneColor {
    BLACK,
    WHITE,
    ;

    fun reverse(): StoneColor {
        return when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }
    }
}
