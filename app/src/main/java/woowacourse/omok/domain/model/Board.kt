package woowacourse.omok.domain.model

import woowacourse.omok.domain.model.Position.Companion.INDEX_RANGE

class Board(private val _board: MutableMap<Position, Stone> = initBoard()) {
    val board: Map<Position, Stone>
        get() = _board.toMap()

    fun emptyPosition(position: Position): Boolean = find(position) == Stone.NONE

    fun find(position: Position): Stone = board[position] ?: Stone.NONE

    fun place(stonePosition: StonePosition) {
        _board[stonePosition.position] = stonePosition.stone
    }

    companion object {
        private fun initBoard() =
            INDEX_RANGE.flatMap { row ->
                INDEX_RANGE.map { col -> Position(row, col) }
            }.associateWith { Stone.NONE }
                .toMutableMap()
    }
}
