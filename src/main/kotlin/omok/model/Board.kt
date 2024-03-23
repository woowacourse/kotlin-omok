package omok.model

import omok.model.Position.Companion.INDEX_RANGE

class Board(private val _board: MutableMap<Position, Stone> = initBoard()) {
    val board: Map<Position, Stone>
        get() = _board.toMap()

    fun place(
        position: Position,
        player: Player,
    ) {
        require(find(position) == Stone.NONE) { DUPLICATION_POSITION_MESSAGE }
        require(player.canPlace(this, position)) { FORBIDDEN_POSITION_MESSAGE }

        _board[position] = player.stone
    }

    fun find(position: Position): Stone = _board[position] ?: throw IllegalArgumentException(INVALID_POSITION_MESSAGE)

    companion object {
        private const val INVALID_POSITION_MESSAGE = "올바르지 않은 위치입니다."
        private const val DUPLICATION_POSITION_MESSAGE = "이미 바둑돌이 있는 위치입니다."
        private const val FORBIDDEN_POSITION_MESSAGE = "바둑돌을 놓을 수 없는 위치입니다."

        private fun initBoard() =
            INDEX_RANGE.flatMap { row ->
                INDEX_RANGE.map { col -> Position(row, col) }
            }.associateWith { Stone.NONE }
                .toMutableMap()
    }
}
