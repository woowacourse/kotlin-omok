enum class Turn {
    BLACK, WHITE;

    fun next(): Turn = when (this) {
        BLACK -> WHITE
        WHITE -> BLACK
    }
}
