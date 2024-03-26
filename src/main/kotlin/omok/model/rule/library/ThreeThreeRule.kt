package omok.model.rule.library

class ThreeThreeRule(currentStone: Int, private val opponentStone: Int) : OmokRule(currentStone, opponentStone) {
    override fun abide(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Boolean = countOpenThrees(board, position) < 2

    private fun countOpenThrees(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Int = directions.sumOf { direction -> checkOpenThree(board, position, direction) }

    private fun checkOpenThree(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Int {
        val (x, y) = position
        val (dx, dy) = direction
        val oppositeDirection = direction.let { (dx, dy) -> Pair(-dx, -dy) }
        val (stone1, blink1) = search(board, position, oppositeDirection)
        val (stone2, blink2) = search(board, position, direction)

        val leftDown = stone1 + blink1
        val left = dx * (leftDown + 1)
        val down = dy * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = dx * (rightUp + 1)
        val up = dy * (rightUp + 1)

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && x - dx * leftDown in X_Edge -> 0
            dy != 0 && y - dy * leftDown in Y_Edge -> 0
            dx != 0 && x + dx * rightUp in X_Edge -> 0
            dy != 0 && y + dy * rightUp in Y_Edge -> 0
            board[y - down ][x - left] == opponentStone -> 0
            board[y + up][x + right] == opponentStone -> 0
            countToWall(board, position, oppositeDirection) + countToWall(board, position, direction) <= 5 -> 0
            else -> 1
        }
    }
}
