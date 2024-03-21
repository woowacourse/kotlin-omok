package omok.model

enum class Color {
    BLACK,
    WHITE,
}

fun Color.change(): Color {
    return if (this == Color.BLACK) {
        Color.WHITE
    } else {
        Color.BLACK
    }
}
