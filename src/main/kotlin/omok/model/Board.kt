package omok.model

class Board {
    val board: List<List<StoneType>>
        get() = _board.toList()
    private val _board: MutableList<MutableList<StoneType>> =
        MutableList(BOARD_SIZE) {
            MutableList(BOARD_SIZE) { StoneType.EMPTY }
        }

    fun isForbidden(stone: Stone): Boolean {
        return RenjuRuleAdaptor.isForbidden(this, stone)
    }

    fun putStone(stone: Stone) {
        _board[stone.point.y][stone.point.x] = stone.type
    }

    operator fun contains(point: Point): Boolean {
        return board[point.y][point.x] != StoneType.EMPTY
    }

    fun isWinCondition(stone: Stone): Boolean {
        return Direction.entries.any { direction ->
            countStonesInDirection(stone, direction.dx, direction.dy) >= WINNING_COUNT
        }
    }

    private fun countStonesInDirection(
        stone: Stone,
        dy: Int,
        dx: Int,
    ): Int {
        val y = stone.point.y
        val x = stone.point.x
        val stoneType = stone.type

        var maxCount = DEFAULT_COUNT
        var count = DEFAULT_COUNT
        for (i in -4..4) {
            val targetY = y + i * dy
            val targetX = x + i * dx
            if (targetY !in 0 until BOARD_SIZE || targetX !in 0 until BOARD_SIZE) continue

            when (board[targetY][targetX]) {
                stoneType -> {
                    count++
                    maxCount = maxOf(maxCount, count)
                }
                StoneType.EMPTY -> continue
                else -> count = DEFAULT_COUNT
            }
        }
        return maxCount
    }

    companion object {
        const val DEFAULT_COUNT = 0
        const val WINNING_COUNT = 5
        private const val BOARD_SIZE = 15
    }
}
