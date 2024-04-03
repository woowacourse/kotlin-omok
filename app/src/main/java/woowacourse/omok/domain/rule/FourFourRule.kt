package woowacourse.omok.domain.rule

class FourFourRule(boardSize: Int) : OmokRule(boardSize) {
    override fun isValidPosition(
        board: List<List<Int>>,
        x: Int,
        y: Int,
    ): Boolean = !checkFourFour(board, x, y)

    private fun checkFourFour(
        board: List<List<Int>>,
        x: Int,
        y: Int,
    ): Boolean =
        directions.sumOf { direction ->
            checkOpenFour(
                board,
                x,
                y,
                direction[0],
                direction[1],
            )
        } >= 2

    private fun checkOpenFour(
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

        when {
            blink1 + blink2 == 2 && stone1 + stone2 == 4 -> return 2
            blink1 + blink2 == 2 && stone1 + stone2 == 5 -> return 2
            stone1 + stone2 != 3 -> return 0
            blink1 + blink2 == 2 -> return 0
        }

        val leftDownValid =
            when {
                dx != 0 && x - dx * leftDown in listOf(MIN_X, boardSize - 1) -> 0
                dy != 0 && y - dy * leftDown in listOf(MIN_Y, boardSize - 1) -> 0
                board[y - down][x - left] == otherStone -> 0
                else -> 1
            }
        val rightUpValid =
            when {
                dx != 0 && x + (dx * rightUp) in listOf(MIN_X, boardSize - 1) -> 0
                dy != 0 && y + (dy * rightUp) in listOf(MIN_Y, boardSize - 1) -> 0
                board[y + up][x + right] == otherStone -> 0
                else -> 1
            }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }
}
