package omok.domain.omokRule

object FourRule : OmokRule() {
    private const val MIN_OPEN_FOURS = 2
    override fun validate(board: List<List<Int>>, point: Pair<Int, Int>): Boolean {
        return countOpenFours(board, point) >= MIN_OPEN_FOURS
    }

    private fun countOpenFours(board: List<List<Int>>, point: Pair<Int, Int>): Int {
        return directions.sumOf { checkOpenFour(board, point, it) }
    }

    private fun checkOpenFour(board: List<List<Int>>, point: Pair<Int, Int>, direction: Pair<Int, Int>): Int {
        val (x, y) = point
        val (dx, dy) = direction
        val (stone1, blink1) = search(board, point, direction)
        val (stone2, blink2) = search(board, point, direction.run { Pair(-this.first, -this.second) })

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        when {
            blink1 + blink2 == 2 && stone1 + stone2 == 4 -> return 2
            blink1 + blink2 == 2 && stone1 + stone2 == 5 -> return 2
            stone1 + stone2 != 3 -> return 0
            blink1 + blink2 == 2 -> return 0
        }

        val leftDownValid = when {
            dx != 0 && x.minus(dx * leftDown) in X_Edge -> 0
            dy != 0 && y.minus(dy * leftDown) in Y_Edge -> 0
            board[x - dx * (leftDown + 1)][y - dy * (leftDown + 1)] == WHITE_STONE -> 0
            else -> 1
        }
        val rightUpValid = when {
            dx != 0 && x.plus(dx * rightUp) in X_Edge -> 0
            dy != 0 && y.plus(dy * rightUp) in Y_Edge -> 0
            board[x + dx * (rightUp + 1)][y + dy * (rightUp + 1)] == WHITE_STONE -> 0
            else -> 1
        }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }
}
