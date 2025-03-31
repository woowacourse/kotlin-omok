package woowacourse.omok.domain.rule.lib

class CountInRowRule(
    currentStone: Int,
    isWinningRule: Boolean,
    val condition: (Int) -> Boolean,
) : OmokMoveRule(currentStone, isWinningRule) {
    override fun validate(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Boolean {
        for (direction in directions) {
            val count = countConsecutiveStones(board, position, direction)
            if (condition(count)) return true
        }
        return false
    }

    private fun countConsecutiveStones(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Int {
        val (x, y) = position
        val (dx, dy) = direction
        var count = 1

        count += countInDirection(board, x, y, dx, dy)
        count += countInDirection(board, x, y, -dx, -dy)

        return count
    }

    private fun countInDirection(
        board: List<List<Int>>,
        startX: Int,
        startY: Int,
        dx: Int,
        dy: Int,
    ): Int {
        var x = startX + dx
        var y = startY + dy
        var count = 0

        while (x in board.indices && y in board.indices) {
            if (board[y][x] == currentStone) {
                count++
                x += dx
                y += dy
            } else {
                break
            }
        }
        return count
    }
}
