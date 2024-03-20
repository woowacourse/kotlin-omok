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
}
