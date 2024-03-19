package omok

enum class Stone {
    BLACK,
    WHITE;

    fun reverse(): Stone {
        return when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }
    }
}
