package omok.domain.player

enum class Stone {
    BLACK, WHITE;
}

fun String.changeToStone() = if (this == "BLACK") Stone.BLACK else Stone.WHITE
