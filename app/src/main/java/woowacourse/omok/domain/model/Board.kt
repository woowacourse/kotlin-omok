package woowacourse.omok.domain.model

import woowacourse.omok.domain.model.Position.Companion.INDEX_RANGE

class Board(private val _board: MutableMap<Position, Stone> = initBoard()) {
    val board: Map<Position, Stone>
        get() = _board.toMap()

    fun emptyPosition(position: Position): Boolean = find(position) == Stone.NONE

    fun validPosition(
        position: Position,
        player: Player,
    ): Boolean = player.canPlace(this, position)

    fun find(position: Position): Stone = board[position] ?: Stone.NONE

    fun place(
        position: Position,
        player: Player,
    ) {
        _board[position] = player.stone
    }

    companion object {
        private fun initBoard() =
            INDEX_RANGE.flatMap { row ->
                INDEX_RANGE.map { col -> Position(row, col) }
            }.associateWith { Stone.NONE }
                .toMutableMap()
    }
}
