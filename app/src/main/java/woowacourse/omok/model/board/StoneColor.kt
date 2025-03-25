package woowacourse.omok.model.board

enum class StoneColor {
    WHITE,
    BLACK,
    ;

    fun reverseStoneColor(): StoneColor = if (this == WHITE) BLACK else WHITE
}
