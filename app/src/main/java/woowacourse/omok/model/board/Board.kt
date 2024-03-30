package woowacourse.omok.model.board

import woowacourse.omok.model.player.Player

class Board(
    val size: Int,
    private var _board: MutableMap<Position, Stone> = initBoard(size),
) {
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

    fun find(position: Position): Stone {
        return _board[position] ?: throw IllegalArgumentException(INVALID_POSITION_MESSAGE)
    }

    fun emptyPosition(block: (Position) -> Boolean): Boolean {
        return _board.all {
            if (it.value == Stone.NONE) block(it.key) else true
        }
    }

    fun isFull(): Boolean {
        return _board.all { it.value != Stone.NONE }
    }

    fun clear() {
        _board = initBoard(size)
    }

    companion object {
        private const val INVALID_POSITION_MESSAGE = "올바르지 않은 위치입니다."
        private const val DUPLICATION_POSITION_MESSAGE = "이미 바둑돌이 있는 위치입니다."
        private const val FORBIDDEN_POSITION_MESSAGE = "바둑돌을 놓을 수 없는 위치입니다."

        private fun initBoard(size: Int) =
            (0 until size).flatMap { row ->
                (0 until size).map { col -> Position(row, col) }
            }.associateWith { Stone.NONE }
                .toMutableMap()
    }
}
