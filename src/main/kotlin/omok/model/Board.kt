package omok.model

import omok.model.state.Black
import omok.model.state.GameState
import omok.model.state.TurnState
import omok.model.state.White

class Board(
    stones: List<Stone> = emptyList(),
) {
    private val _status: Array<Array<Color?>> = Array(COMPUTATION_BOARD_SIZE) { Array(COMPUTATION_BOARD_SIZE) { null } }
    val status: List<List<Color?>>
        get() = _status.map { it.toList() }.toList()

    var stones: List<Stone> = stones.toList()
        private set

    private val turnState: TurnState
        get() = if (isEven(stones.size)) Black(_status) else White(_status)

    fun place(position: Position): GameState {
        if (position.horizontalCoordinate.index !in MIN_INDEX..MAX_INDEX) return GameState.Error(message = MESSAGE_WRONG_ROW_RANGE)
        if (position.verticalCoordinate.index !in MIN_INDEX..MAX_INDEX) return GameState.Error(message = MESSAGE_WRONG_COL_RANGE)
        if (position in stones.map { it.position }) return GameState.Error(message = MESSAGE_DUPLICATED_POSITION)
        if (stones.size >= DISPLAY_BOARD_SIZE * DISPLAY_BOARD_SIZE) return GameState.GameOver(gameResult = GameResult.DRAW)

        val turnResult = turnState.getWinningResult(position, ::markSinglePlace, ::addSingleStone)
        return turnResult?.let { GameState.GameOver(turnResult) } ?: GameState.OnProgress
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
                Color.BLACK -> stones.plus(Stone.Black(Position.of(position.horizontalCoordinate.index, position.verticalCoordinate.index)))
                Color.WHITE -> stones.plus(Stone.White(Position.of(position.horizontalCoordinate.index, position.verticalCoordinate.index)))
            }
    }

    private fun markSinglePlace(
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
        color: Color,
    ) {
        _status[horizontalCoordinate][verticalCoordinate] = color
    }

    companion object {
        private const val MESSAGE_DUPLICATED_POSITION = "중복된 곳에 착수할 수 없습니다."
        private const val ODD_EVEN_INDICATOR = 2
        private const val COMPUTATION_BOARD_SIZE = 16
        private const val DISPLAY_BOARD_SIZE = COMPUTATION_BOARD_SIZE - 1
        private const val MIN_INDEX = 1
        private const val MAX_INDEX = 15
        private const val MIN_COL = 'A'
        private const val MAX_COL = 'O'
        private const val MESSAGE_WRONG_ROW_RANGE = "$MIN_INDEX 부터 ${MAX_INDEX}사이의 정수를 입력해야 합니다"
        private const val MESSAGE_WRONG_COL_RANGE = "$MIN_COL 부터 ${MAX_COL}사이의 알파벳을 입력해야 합니다"
    }
}
