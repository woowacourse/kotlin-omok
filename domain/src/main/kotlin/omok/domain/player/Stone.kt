package omok.domain.player

enum class Stone {
    BLACK, WHITE;
}

fun changeToStone(value: Int): Stone {
    return if (value == 0) Stone.BLACK
    else Stone.WHITE
}
