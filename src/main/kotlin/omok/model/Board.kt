package omok.model

import omok.model.search.AscendingDfs
import omok.model.search.DescendingDfs
import omok.model.search.HorizontalDfs
import omok.model.search.VerticalDfs
import omok.model.state.Black
import omok.model.state.TurnState
import omok.model.state.White

class Board(
    notation: List<Stone> = emptyList(),
    private val _status: Array<Array<Color>> = Array(ARRAY_SIZE) { Array(ARRAY_SIZE) { Color.NONE } },
) {
    var notation: List<Stone> = notation.toList()
        private set

    val status: List<List<Color>>
        get() = _status.map { it.toList() }.toList()

    val turn: Color
        get() = if (isEven(notation.size)) Color.BLACK else Color.WHITE

    private val turnState: TurnState
        get() = if (turn == Color.BLACK) Black(_status) else White()

    fun place(position: Position) {
        require(!isDuplicated(position)) { EXCEPTION_DUPLICATED_POSITION }
        turnState.addStone(position, ::placeStone)
    }

    fun getGameResult(position: Position): GameResult {
        val row = ARRAY_SIZE - position.row.value
        val col = position.col.value
        val color = status[row][col]
        if (isCurrentTurnWin(position)) {
            return GameResult.entries.first { it.color == color }
        }
        if (isBoardFull()) return GameResult.DRAW
        return GameResult.PROCEEDING
    }

    private fun isDuplicated(position: Position): Boolean {
        val row = ARRAY_SIZE - position.row.value
        val col = position.col.value
        return _status[row][col] != Color.NONE
    }

    private fun placeStone(position: Position) {
        val row = position.row.value
        val col = position.col.title
        _status[ARRAY_SIZE - position.row.value][position.col.value] = turn
        notation =
            when (turn) {
                Color.BLACK -> notation.plus(Stone.Black(Position.of(row, col)))
                Color.WHITE -> notation.plus(Stone.White(Position.of(row, col)))
                Color.NONE -> notation
            }
    }

    private fun isCurrentTurnWin(position: Position): Boolean {
        val row = ARRAY_SIZE - position.row.value
        val col = position.col.value
        val color = status[row][col]
        val verticalCount = VerticalDfs(_status).apply { search(color, row, col) }.count
        val horizontalCount = HorizontalDfs(_status).apply { search(color, row, col) }.count
        val ascendingCount = AscendingDfs(_status).apply { search(color, row, col) }.count
        val descendingCount = DescendingDfs(_status).apply { search(color, row, col) }.count
        return listOf(verticalCount, horizontalCount, ascendingCount, descendingCount).any { it >= 5 }
    }

    private fun isBoardFull(): Boolean {
        return status.all { oneLine ->
            isFull(oneLine)
        }
    }

    private fun isFull(oneLine: List<Color>): Boolean {
        return oneLine.all { color ->
            color != Color.NONE
        }
    }

    private fun isEven(num: Int): Boolean {
        return num % ODD_EVEN_INDICATOR == 0
    }

    companion object {
        private const val EXCEPTION_DUPLICATED_POSITION = "중복된 곳에 착수할 수 없습니다."
        private const val ODD_EVEN_INDICATOR = 2
        const val BOARD_SIZE = 15
        const val ARRAY_SIZE = BOARD_SIZE + 1
    }
}
