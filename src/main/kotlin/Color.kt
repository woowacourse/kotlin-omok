enum class Color {
    BLACK,
    WHITE;

    operator fun not(): Color {
        return if (this == BLACK) {
            WHITE
        } else {
            BLACK
        }
    }
}
