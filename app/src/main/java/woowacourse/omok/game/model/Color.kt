package woowacourse.omok.game.model

enum class Color {
    BLACK,
    WHITE,
}

fun Color.isWhite(): Boolean = this == Color.WHITE

fun Color.isBlack(): Boolean = this == Color.BLACK
