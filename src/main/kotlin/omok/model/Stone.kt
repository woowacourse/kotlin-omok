package omok.model

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
