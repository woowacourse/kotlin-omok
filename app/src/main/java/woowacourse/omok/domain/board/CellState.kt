package woowacourse.omok.domain.board

enum class CellState {
    WHITE,
    BLACK,
    EMPTY,
    ;

    fun reverseCellState(): CellState {
        if (this == EMPTY) return EMPTY
        return if (this == WHITE) BLACK else WHITE
    }
}
