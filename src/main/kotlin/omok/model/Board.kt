package omok.model

import omok.model.Position.Companion.INDEX_RANGE

class Board {
    private val board: MutableMap<Position, Stone> = initBoard()

    private fun initBoard() =
        INDEX_RANGE.flatMap { row ->
            INDEX_RANGE.map { col -> Position(row, col) }
        }.associateWith { Stone.NONE }
            .toMutableMap()

    fun place(
        position: Position,
        stone: Stone,
    ) {
        require(find(position) == Stone.NONE) { "이미 바둑돌이 있는 위치입니다." }
        board[position] = stone
    }

    fun find(position: Position): Stone = board[position] ?: throw IllegalArgumentException("올바르지 않은 위치입니다.")
}
