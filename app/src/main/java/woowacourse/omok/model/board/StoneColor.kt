package woowacourse.omok.model.board

enum class StoneColor {
    WHITE,
    BLACK,
    NONE,
    ;

    fun reverseStoneColor(): StoneColor {
        if (this == NONE) return NONE
        return if (this == WHITE) BLACK else WHITE
    }
}
