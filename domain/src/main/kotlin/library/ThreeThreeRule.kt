package library

class ThreeThreeRule(boardSize: Int) : OmokRule(boardSize) {
    override fun validate(board: List<List<Int>>, position: Pair<Int, Int>): Boolean =
        countOpenThrees(board, position) >= 2

    private fun countOpenThrees(board: List<List<Int>>, position: Pair<Int, Int>): Int =
        directions.sumOf { direction -> checkOpenThree(board, position, direction) }

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
            dx != 0 && x - dx * leftDown in xEdge -> 0
            dy != 0 && y - dy * leftDown in yEdge -> 0
            dx != 0 && x + dx * rightUp in xEdge -> 0
            dy != 0 && y + dy * rightUp in yEdge -> 0
            board[y - down][x - left] == WHITE_STONE -> 0
            board[y + up][x + right] == WHITE_STONE -> 0
            countToWall(board, position, oppositeDirection) + countToWall(
                board,
                position,
                direction
            ) <= 5 -> 0
            else -> 1
        }
    }
}