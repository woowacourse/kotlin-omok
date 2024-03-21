package omok.model

import omok.model.state.Black
import omok.model.state.TurnState
import omok.model.state.White

class Board(
    stones: List<Stone> = emptyList(),
    private val _status: Array<Array<Color?>> = Array(ARRAY_SIZE) { Array(ARRAY_SIZE) { null } },
) {
    val status: List<List<Color?>>
        get() = _status.map { it.toList() }.toList()

    var stones: List<Stone> = stones.toList()
        private set

    private val turnState: TurnState
        get() = if (isEven(stones.size)) Black(_status) else White(_status)

    fun place(position: Position): GameResult? {
        require(position !in stones.map { it.position }) { EXCEPTION_DUPLICATED_POSITION }
        if (stones.size >= BOARD_SIZE * BOARD_SIZE) return GameResult.DRAW
        return turnState.getWinningResult(position, ::markSinglePlace, ::addSingleStone)
    }

    private fun isEven(num: Int): Boolean {
        return num % ODD_EVEN_INDICATOR == 0
    }

    private fun addSingleStone(
        color: Color,
        position: Position,
    ) {
        stones =
            when (color) {
                Color.BLACK -> stones.plus(Stone.Black(Position.of(position.row.value, position.col.title)))
                Color.WHITE -> stones.plus(Stone.White(Position.of(position.row.value, position.col.title)))
            }
    }

    private fun markSinglePlace(
        row: Int,
        col: Int,
        color: Color,
    ) {
        _status[row][col] = color
    }

    companion object {
        private const val EXCEPTION_DUPLICATED_POSITION = "중복된 곳에 착수할 수 없습니다."
        private const val ODD_EVEN_INDICATOR = 2
        private const val ARRAY_SIZE = 16
        private const val BOARD_SIZE = ARRAY_SIZE - 1
    }
}
