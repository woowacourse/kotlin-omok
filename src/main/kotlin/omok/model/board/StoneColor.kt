package omok.model.board

enum class StoneColor {
    WHITE,
    BLACK,
    ;

    fun reverseStoneColor(): StoneColor {
        return if (this == WHITE) BLACK else WHITE
    }
}
