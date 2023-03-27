package omok.domain.omokRule

object ThreeThreeRule : OmokRule() {
    override fun validate(board: List<List<Int>>, position: RulePosition): Boolean =
        countOpenThrees(board, position) >= 2

    private fun countOpenThrees(board: List<List<Int>>, position: RulePosition): Int =
        RuleDirection.DIRECTIONS.sumOf { direction -> checkOpenThree(board, position, direction) }

    private fun checkOpenThree(
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

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && x - dx * leftDown in X_Edge -> 0
            dy != 0 && y - dy * leftDown in Y_Edge -> 0
            dx != 0 && x + dx * rightUp in X_Edge -> 0
            dy != 0 && y + dy * rightUp in Y_Edge -> 0
            board[y - down ][x - left] == WHITE_STONE -> 0
            board[y + up][x + right] == WHITE_STONE -> 0
            countToWall(board, position, direction.opposite()) + countToWall(board, position, direction) <= 5 -> 0
            else -> 1
        }
    }
}
