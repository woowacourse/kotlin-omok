package omok.model

enum class GoStoneColor {
    BLACK, WHITE;

    companion object {
        fun getNextColor(color: GoStoneColor?): GoStoneColor = when (color) {
            null -> BLACK
            BLACK -> WHITE
            WHITE -> BLACK
        }
    }
}
