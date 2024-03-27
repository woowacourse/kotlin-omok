package omok.model.rule.library

class FourFourRule(boardSize: Int) : OmokRule(boardSize) {
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
        return directions.sumOf { direction -> checkOpenFour(board, position, direction) }
    }

    private fun checkOpenFour(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Int =
        with(omokRuleData(board, position, direction)) {
            val (x, y) = position
            val (dx, dy) = direction

            when {
                blink1 + blink2 == 2 && stone1 + stone2 == 4 -> return 2
                blink1 + blink2 == 2 && stone1 + stone2 == 5 -> return 2
                stone1 + stone2 != 3 -> return 0
                blink1 + blink2 == 2 -> return 0
            }

            val leftDownValid =
                when {
                    dx != 0 && x - dx * leftDown in edges -> 0
                    dy != 0 && y - dy * leftDown in edges -> 0
                    board[y - down][x - left] == opponentStone -> 0
                    else -> 1
                }
            val rightUpValid =
                when {
                    dx != 0 && x + (dx * rightUp) in edges -> 0
                    dy != 0 && y + (dy * rightUp) in edges -> 0
                    board[y + up][x + right] == opponentStone -> 0
                    else -> 1
                }

            return if (leftDownValid + rightUpValid >= 1) 1 else 0
        }
}
