package omok.library

class FourFourRule(boardSize: Int) : OmokRule(boardSize) {
    override fun validate(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Boolean = countOpenThrees(board, position) >= REQUIRED_OPEN_THREES_FOR_FOUR_FOUR

    private fun countOpenThrees(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Int = directions.sumOf { direction -> checkOpenFour(board, position, direction) }

    private fun checkOpenFour(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Int {
        val (x, y) = position
        val (dx, dy) = direction
        val oppositeDirection = Pair(-dx, -dy)
        val (stone1, blink1) = search(board, position, oppositeDirection)
        val (stone2, blink2) = search(board, position, direction)

        when {
            blink1 + blink2 == TOTAL_BLINKS_FOR_VALID_POSITION && stone1 + stone2 in VALID_STONE_COMBINATION_FOR_FOUR_FOUR
            -> return DOUBLE_OPEN_FOUR

            stone1 + stone2 != TOTAL_STONES_FOR_OPEN_THREE -> return NO_OPEN_THREE
            blink1 + blink2 == TOTAL_BLINKS_FOR_INVALID_POSITION -> return NO_OPEN_THREE
        }

        val isLeftDownValid = checkValidity(dx, x, stone1, dy, y, board, blink1)
        val isRightUpValid = checkValidity(dx, x, stone2, dy, y, board, blink2)

        return if (isLeftDownValid || isRightUpValid) SINGLE_OPEN_FOUR else NO_OPEN_THREE
    }

    private fun checkValidity(
        dx: Int,
        x: Int,
        stones: Int,
        dy: Int,
        y: Int,
        board: List<List<Int>>,
        blinks: Int,
    ): Boolean {
        val newX = x + dx * (stones + blinks)
        val newY = y + dy * (stones + blinks)

        return !(dx != NO_DIRECTION && newX in xEdge || dy != NO_DIRECTION && newY in yEdge || board[newY][newX] == opponentStone)
    }

    companion object {
        const val REQUIRED_OPEN_THREES_FOR_FOUR_FOUR = 2
        const val TOTAL_BLINKS_FOR_VALID_POSITION = 2
        const val TOTAL_BLINKS_FOR_INVALID_POSITION = 2
        val VALID_STONE_COMBINATION_FOR_FOUR_FOUR = 4..5
        const val DOUBLE_OPEN_FOUR = 2
        const val SINGLE_OPEN_FOUR = 1
        const val NO_OPEN_THREE = 0
        const val TOTAL_STONES_FOR_OPEN_THREE = 3
        const val NO_DIRECTION = 0
    }
}
