package omok.model.board

enum class PointState {
    WHITE,
    BLACK,
    OPEN,
    ;

    fun reverseStoneColor(): PointState {
        return if (this == WHITE) BLACK else WHITE
    }
}
