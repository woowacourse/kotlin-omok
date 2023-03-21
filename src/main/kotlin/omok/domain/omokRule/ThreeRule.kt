package omok.domain.omokRule

object ThreeRule : OmokRule() {
    private const val MIN_OPEN_THREES = 2
    override fun validate(board: List<List<Int>>, point: Pair<Int, Int>): Boolean {
        return countOpenThrees(board, point) >= MIN_OPEN_THREES
    }

    private fun countOpenThrees(board: List<List<Int>>, point: Pair<Int, Int>): Int {
        return directions.sumOf { checkOpenTree(board, point, it) }
    }

    private fun checkOpenTree(board: List<List<Int>>, point: Pair<Int, Int>, direction: Pair<Int, Int>): Int {
        val (x, y) = point
        val (dx, dy) = direction
        val (stone1, blink1) = search(board, point, direction)
        val (stone2, blink2) = search(board, point, direction.run { Pair(-this.first, -this.second) })

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && x.minus(leftDown) in X_Edge -> 0
            dy != 0 && y.minus(leftDown) in X_Edge -> 0
            dx != 0 && x.plus(rightUp) in X_Edge -> 0
            dy != 0 && y.plus(rightUp) in X_Edge -> 0
            board[x - dx * (leftDown + 1)][y - dy * (leftDown + 1)] == WHITE_STONE -> 0
            board[x + dx * (rightUp + 1)][y + dy * (rightUp + 1)] == WHITE_STONE -> 0
            countToWall(board, point, direction.run { Pair(-this.first, -this.second) }) + countToWall(board, point, direction) <= 5 -> 0
            else -> 1
        }
    }
}
