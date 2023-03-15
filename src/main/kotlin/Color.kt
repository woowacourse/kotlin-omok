enum class Color {
    BLACK,
    WHITE;

    operator fun not(): Color = when (this) {
        BLACK -> WHITE
        WHITE -> BLACK
    }
}
