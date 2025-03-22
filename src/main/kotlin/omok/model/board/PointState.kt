package omok.model.board

enum class PointState {
    WHITE,
    BLACK,
    OPEN,
    ;

    fun reverseStoneColor(): PointState? {
        if (this == OPEN) return null

        return if (this == WHITE) BLACK else WHITE
    }
}
