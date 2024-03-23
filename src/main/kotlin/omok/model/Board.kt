package omok.model

import omok.model.search.AscendingDfs
import omok.model.search.DescendingDfs
import omok.model.search.HorizontalDfs
import omok.model.search.VerticalDfs
import omok.model.state.Black
import omok.model.state.TurnState
import omok.model.state.White

class Board(
    stones: List<Stone> = emptyList(),
    private val _status: Array<Array<Color>> = Array(ARRAY_SIZE) { Array(ARRAY_SIZE) { Color.NONE } },
) {
    val status: List<List<Color>>
        get() = _status.map { it.toList() }.toList()

    var stones: List<Stone> = stones.toList()
        private set

    private val lastStoneColor: Color
        get() = if (isEven(stones.size)) Color.WHITE else Color.BLACK

    private val nextStoneColor: Color
        get() = if (isEven(stones.size)) Color.BLACK else Color.WHITE

    private val turnState: TurnState
        get() = if (isEven(stones.size)) Black(_status) else White()

    fun place(position: Position) {
        require(position !in stones.map { it.position }) { EXCEPTION_DUPLICATED_POSITION }
        turnState.addStone(position, ::placeStone)
    }

    private fun isEven(num: Int): Boolean {
        return num % ODD_EVEN_INDICATOR == 0
    }

    fun getGameResult(position: Position): GameResult {
        if (isCurrentTurnWin(position)) {
            return GameResult.entries.first { it.color == lastStoneColor }
        }
        if (stones.size >= BOARD_SIZE * BOARD_SIZE) return GameResult.DRAW
        return GameResult.PROCEEDING
    }

    private fun isCurrentTurnWin(position: Position): Boolean {
        val row = ARRAY_SIZE - position.row.value
        val col = position.col.value
        val verticalCount = VerticalDfs(_status).apply { search(lastStoneColor, row, col) }.count
        val horizontalCount = HorizontalDfs(_status).apply { search(lastStoneColor, row, col) }.count
        val ascendingCount = AscendingDfs(_status).apply { search(lastStoneColor, row, col) }.count
        val descendingCount = DescendingDfs(_status).apply { search(lastStoneColor, row, col) }.count
        return listOf(verticalCount, horizontalCount, ascendingCount, descendingCount).any { it >= 5 }
    }

    private fun placeStone(position: Position) {
        val row = position.row.value
        val col = position.col.title
        stones =
            when (nextStoneColor) {
                Color.BLACK -> stones.plus(Stone.Black(Position.of(row, col)))
                Color.WHITE -> stones.plus(Stone.White(Position.of(row, col)))
                Color.NONE -> stones
            }
        _status[ARRAY_SIZE - position.row.value][position.col.value] = lastStoneColor
    }

    companion object {
        private const val EXCEPTION_DUPLICATED_POSITION = "중복된 곳에 착수할 수 없습니다."
        private const val ODD_EVEN_INDICATOR = 2
        const val BOARD_SIZE = 15
        const val ARRAY_SIZE = BOARD_SIZE + 1
    }
}
