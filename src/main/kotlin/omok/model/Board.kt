package omok.model

import omok.model.Position.Companion.INDEX_RANGE

class Board(private val _board: MutableMap<Position, Stone> = initBoard()) {
    val board: Map<Position, Stone>
        get() = _board.toMap()

    fun emptyPosition(position: Position): Boolean = find(position) == Stone.NONE

    fun validPosition(
        position: Position,
        player: Player,
    ): Boolean = player.canPlace(this, position)

    fun find(position: Position): Stone = board[position] ?: Stone.NONE

    fun place2(
        position: Position,
        player: Player,
    ) {
        _board[position] = player.stone
    }

    fun place(
        position: Position,
        player: Player,
    ) {
        check(find(position) == Stone.NONE) { "이미 바둑돌이 있는 위치입니다." }
        check(player.canPlace(this, position)) { "바둑돌을 놓을 수 없는 위치입니다." }

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
