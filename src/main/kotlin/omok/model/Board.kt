package omok.model

import omok.model.Position.Companion.INDEX_RANGE

class Board(private val _board: MutableMap<Position, Stone> = initBoard()) {
    val board: Map<Position, Stone>
        get() = _board.toMap()

    fun place(
        position: Position,
        player: Player
    ) {
        require(find(position) == Stone.NONE) { "이미 바둑돌이 있는 위치입니다." }
        require(player.canPlace(this, position)) { "바둑돌을 놓을 수 없는 위치입니다." }

        _board[position] = player.stone
    }

    fun find(position: Position): Stone = _board[position] ?: throw IllegalArgumentException("올바르지 않은 위치입니다.")

    fun isWin(position: Position): Boolean {
        Direction.biDirections().forEach { (direction1, direction2) ->
            var count = 1
            count += continualCount(position, direction1)
            count += continualCount(position, direction2)

            if (count >= 5) return true
        }
        return false
    }

    private fun continualCount(
        position: Position,
        direction: Direction,
    ): Int {
        val myStone = find(position)
        var count = 0
        var nowPos = position

        while (true) {
            nowPos = nowPos.move(direction) ?: return count
            if (find(nowPos) != myStone) return count
            count++
        }
    }

    companion object {
        private fun initBoard() =
            INDEX_RANGE.flatMap { row ->
                INDEX_RANGE.map { col -> Position(row, col) }
            }.associateWith { Stone.NONE }
                .toMutableMap()
    }
}
