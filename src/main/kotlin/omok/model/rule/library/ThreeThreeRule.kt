package omok.model.rule.library

class ThreeThreeRule(boardSize: Int) : OmokRule(boardSize) {
    override fun abide(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Boolean {
        return countOpenThrees(board, position) < 2
    }

    private fun countOpenThrees(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Int {
        return directions.sumOf { direction -> checkOpenThree(board, position, direction) }
    }

    private fun checkOpenThree(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Int =
        with(omokRuleData(board, position, direction)) {
            val (x, y) = position
            val (dx, dy) = direction
            val oppositeDirection = -dx to -dy

            return when {
                stone1 + stone2 != 2 -> 0
                blink1 + blink2 == 2 -> 0
                dx != 0 && x - dx * leftDown in edges -> 0
                dy != 0 && y - dy * leftDown in edges -> 0
                dx != 0 && x + dx * rightUp in edges -> 0
                dy != 0 && y + dy * rightUp in edges -> 0
                board[y - down][x - left] == WHITE_STONE -> 0
                board[y + up][x + right] == WHITE_STONE -> 0
                countToWall(board, position, oppositeDirection) + countToWall(board, position, direction) <= 5 -> 0
                else -> 1
            }
        }
}
