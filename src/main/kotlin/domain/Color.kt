package domain

enum class Color {
    BLACK,
    WHITE;

    fun turnColor(): Color = if (this == BLACK) WHITE else BLACK
}
