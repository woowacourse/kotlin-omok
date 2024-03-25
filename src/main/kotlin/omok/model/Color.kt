package omok.model

enum class Color {
    BLACK,
    WHITE,
}

fun Color.isWhite(): Boolean = this == Color.WHITE

fun Color.isBLACK(): Boolean = this == Color.BLACK
