package omok.model

interface Rule {
    fun isWinCondition(
        board: List<List<StoneType>>,
        stone: Stone,
    ): Boolean {
        return Direction.entries.any { direction ->
            checkCount(countStonesInDirection(board, stone.point.y, stone.point.x, stone.type, direction.dx, direction.dy))
        }
    }

    fun countStonesInDirection(
        board: List<List<StoneType>>,
        y: Int,
        x: Int,
        stoneType: StoneType,
        dy: Int,
        dx: Int,
    ): Int {
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

    fun checkCount(count: Int): Boolean

    companion object {
        const val DEFAULT_COUNT = 0
        const val WINNING_COUNT = 5
        const val BOARD_SIZE = 15
    }
}

object BlackRule : Rule {
    override fun checkCount(count: Int): Boolean = count == Rule.WINNING_COUNT

    fun isForbidden(
        board: List<List<StoneType>>,
        stone: Stone,
    ): Boolean {
        return RenjuRule.isForbidden(board, stone)
    }
}

object WhiteRule : Rule {
    override fun checkCount(count: Int): Boolean = count >= Rule.WINNING_COUNT
}
