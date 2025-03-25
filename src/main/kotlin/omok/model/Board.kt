package omok.model

import omok.model.position.Col
import omok.model.position.Row

class Board(
    val col: Col = Col(DEFAULT_COL_SIZE),
    val row: Row = Row(DEFAULT_ROW_SIZE),
) {
    private val _stones: MutableSet<Stone> = mutableSetOf()
    val stones: Set<Stone> get() = _stones.toSet()

    fun add(newStone: Stone): MoveResult {
        require(newStone.position.x.value in 1..col.value) { ERROR_MESSAGE_INVALID_COL.format(col.value) }
        require(newStone.position.y.value in 1..row.value) { ERROR_MESSAGE_INVALID_ROW.format(row.value) }
        if (_stones.map { stone -> stone.position }.contains(newStone.position)) {
            return MoveResult.Failure.PositionAlreadyOccupied
        }
        _stones.add(newStone)
        return MoveResult.Success.Playing
    }

    fun filterStones(color: Color): List<Stone> = _stones.filter { stone -> stone.color == color }

    companion object {
        private const val DEFAULT_COL_SIZE = 15
        private const val DEFAULT_ROW_SIZE = 15

        private const val ERROR_MESSAGE_INVALID_COL = "바둑돌은 열 1과 %s 사이에만 둘 수 있습니다."
        private const val ERROR_MESSAGE_INVALID_ROW = "바둑돌은 행 1과 %s 사이에만 둘 수 있습니다."
    }
}
