package woowacourse.omok.model.board

data class Position(val row: Int, val col: Int) {
    fun move(direction: Direction): Position {
        return Position(row + direction.row, col + direction.col)
    }
}
