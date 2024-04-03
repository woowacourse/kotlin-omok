package woowacourse.omok.domain.rule

class ThreeThreeRule(boardSize: Int) : OmokRule(boardSize) {
    override fun isValidPosition(
        board: List<List<Int>>,
        x: Int,
        y: Int,
    ): Boolean = !checkThreeThree(board, x, y)

    private fun checkThreeThree(
        board: List<List<Int>>,
        x: Int,
        y: Int,
    ): Boolean =
        directions.sumOf { direction ->
            checkOpenThree(
                board,
                x,
                y,
                direction[0],
                direction[1],
            )
        } >= 2

    private fun checkOpenThree(
        board: List<List<Int>>,
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
        val (stone1, blink1) = search(board, x, y, -dx, -dy)
        val (stone2, blink2) = search(board, x, y, dx, dy)

        val leftDown = stone1 + blink1
        val left = dx * (leftDown + 1)
        val down = dy * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = dx * (rightUp + 1)
        val up = dy * (rightUp + 1)

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && x - leftDown in listOf(MIN_X, boardSize - 1) -> 0
            dy != 0 && y - leftDown in listOf(MIN_Y, boardSize - 1) -> 0
            dx != 0 && x + rightUp in listOf(MIN_X, boardSize - 1) -> 0
            dy != 0 && y + rightUp in listOf(MIN_Y, boardSize - 1) -> 0
            board[y - down][x - left] == otherStone -> 0
            board[y + up][x + right] == otherStone -> 0
            countToWall(board, x, y, -dx, -dy) + countToWall(board, x, y, dx, dy) <= 5 -> 0
            else -> 1
        }
    }

    private fun countToWall(
        board: List<List<Int>>,
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
        var toRight = x
        var toTop = y
        var distance = 0
        while (true) {
            if (dx > 0 && toRight == boardSize - 1) break
            if (dx < 0 && toRight == MIN_X) break
            if (dy > 0 && toTop == boardSize - 1) break
            if (dy < 0 && toTop == MIN_X) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                in listOf(currentStone, EMPTY_STONE) -> distance++
                otherStone -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }
}
