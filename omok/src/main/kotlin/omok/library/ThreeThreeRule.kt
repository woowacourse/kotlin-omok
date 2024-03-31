package omok.library

class ThreeThreeRule(boardSize: Int) : OmokRule(boardSize) {
    override fun validate(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Boolean = countOpenThrees(board, position) >= VALID_CONDITION

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
        val left = dx * (leftDown + GO_NEXT)
        val down = dy * (leftDown + GO_NEXT)
        val rightUp = stone2 + blink2
        val right = dx * (rightUp + GO_NEXT)
        val up = dy * (rightUp + GO_NEXT)
        return when {
            stone1 + stone2 != CONTINUOUS_STONE -> NO_OPEN_THREE
            blink1 + blink2 == CONTINUOUS_STONE -> NO_OPEN_THREE
            dx != NO_DIRECTION && x - dx * leftDown in xEdge -> NO_OPEN_THREE
            dy != NO_DIRECTION && y - dy * leftDown in yEdge -> NO_OPEN_THREE
            dx != NO_DIRECTION && x + dx * rightUp in xEdge -> NO_OPEN_THREE
            dy != NO_DIRECTION && y + dy * rightUp in yEdge -> NO_OPEN_THREE
            board[y - down][x - left] == WHITE_STONE -> NO_OPEN_THREE
            board[y + up][x + right] == WHITE_STONE -> NO_OPEN_THREE
            countToWall(board, position, oppositeDirection) +
                countToWall(
                    board,
                    position,
                    direction,
                ) <= WIN_CONDITION -> NO_OPEN_THREE

            else -> YES_OPEN_THREE
        }
    }

    companion object {
        private const val NO_DIRECTION = 0
        private const val GO_NEXT = 1
        private const val NO_OPEN_THREE = 0
        private const val YES_OPEN_THREE = 1
        private const val CONTINUOUS_STONE = 2
        private const val WIN_CONDITION = 5
        private const val VALID_CONDITION = 2
    }
}
