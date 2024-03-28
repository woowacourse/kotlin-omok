package omok.model

import omok.model.Position.Companion.INDEX_RANGE

class Board(private val _board: MutableMap<Position, Stone> = initBoard()) {
    val board: Map<Position, Stone>
        get() = _board.toMap()

    fun place(
        position: Position,
        player: Player,
    ) {
        check(find(position) == Stone.NONE) { "이미 바둑돌이 있는 위치입니다." }
        check(player.canPlace(this, position)) { "바둑돌을 놓을 수 없는 위치입니다." }

        _board[position] = player.stone
    }

    fun place(
        position: Position,
        player: Player2,
    ) {
        check(find(position) == Stone.NONE) { "이미 바둑돌이 있는 위치입니다." }
        check(player.canPlace(this, position)) { "바둑돌을 놓을 수 없는 위치입니다." }

        _board[position] = player.stone
    }

    fun find(position: Position): Stone = _board[position] ?: throw IllegalArgumentException("올바르지 않은 위치입니다.")

    companion object {
        private fun initBoard() =
            INDEX_RANGE.flatMap { row ->
                INDEX_RANGE.map { col -> Position(row, col) }
            }.associateWith { Stone.NONE }
                .toMutableMap()
    }
}
