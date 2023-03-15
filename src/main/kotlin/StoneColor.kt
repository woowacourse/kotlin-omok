enum class StoneColor {
    BLACK, WHITE;

    fun next(): StoneColor = when (this) {
        BLACK -> WHITE
        WHITE -> BLACK
    }
}
