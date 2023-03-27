package omok.domain.omokRule

object FourFourRule : OmokRule() {
    override fun validate(board: List<List<Int>>, position: RulePosition): Boolean =
        countOpenThrees(board, position) >= 2

    private fun countOpenThrees(board: List<List<Int>>, position: RulePosition): Int =
        RuleDirection.DIRECTIONS.sumOf { direction -> checkOpenFour(board, position, direction) }

    private fun checkOpenFour(
        board: List<List<Int>>,
        position: RulePosition,
        direction: RuleDirection,
    ): Int {
        val (x, y) = position
        val (dx, dy) = direction

        val (stone1, blink1) = search(board, position, direction.opposite())
        val (stone2, blink2) = search(board, position, direction)

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

        val leftDownValid = when {
            dx != 0 && position.x - dx * leftDown in X_Edge -> 0
            dy != 0 && position.y - dy * leftDown in Y_Edge -> 0
            board[y - down][x - left] == opponentStone -> 0
            else -> 1
        }
        val rightUpValid = when {
            dx != 0 && x + (dx * rightUp) in X_Edge -> 0
            dy != 0 && y + (dy * rightUp) in Y_Edge -> 0
            board[y + up][x + right] == opponentStone -> 0
            else -> 1
        }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }
}
