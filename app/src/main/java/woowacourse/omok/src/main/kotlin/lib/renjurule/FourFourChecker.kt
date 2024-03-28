package woowacourse.omok.src.main.kotlin.lib.renjurule

object FourFourChecker : OmokRule() {
    private const val NOT_FOUR = 0
    private const val FIND_FOUR = 1
    private const val FIND_FOUR_STANDARD = 1
    private const val FOUR_FOUR_STANDARD_COUNT = 2
    private const val INVALID_BLANK_ALLOWANCE = 2
    private const val INVALID_FOUR_FOUR_STONE_COUNT = 3
    private const val FOUR_STONE_COUNT = 4
    private const val FIVE_STONE_COUNT = 5

    fun checkFourFour(
        board: Array<Array<Int>>,
        x: Int,
        y: Int,
    ): Boolean = directions.sumOf { direction -> checkOpenFour(board, x, y, direction[0], direction[1]) } >= 2

    private fun checkOpenFour(
        board: Array<Array<Int>>,
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
        val (stone1, blink1) = search(board, x, y, -dx, -dy)
        val (stone2, blink2) = search(board, x, y, dx, dy)

        val leftDown = stone1 + blink1
        val left = dx * (leftDown + COORDINATION_MOVE_OFFSET)
        val down = dy * (leftDown + COORDINATION_MOVE_OFFSET)

        val rightUp = stone2 + blink2
        val right = dx * (rightUp + COORDINATION_MOVE_OFFSET)
        val up = dy * (rightUp + COORDINATION_MOVE_OFFSET)

        when {
            blink1 + blink2 == INVALID_BLANK_ALLOWANCE && stone1 + stone2 == FOUR_STONE_COUNT -> return FOUR_FOUR_STANDARD_COUNT
            blink1 + blink2 == INVALID_BLANK_ALLOWANCE && stone1 + stone2 == FIVE_STONE_COUNT -> return FOUR_FOUR_STANDARD_COUNT
            stone1 + stone2 != INVALID_FOUR_FOUR_STONE_COUNT -> return NOT_FOUR
            blink1 + blink2 == INVALID_BLANK_ALLOWANCE -> return NOT_FOUR
        }

        val leftDownValid =
            when {
                dx != DIRECTION_STANDARD && x - dx * leftDown in listOf(MIN_X, BOARD_SIZE - 1) -> NOT_FOUR
                dy != DIRECTION_STANDARD && y - dy * leftDown in listOf(MIN_Y, BOARD_SIZE - 1) -> NOT_FOUR
                board[y - down][x - left] == OTHER_STONETYPE -> NOT_FOUR
                else -> FIND_FOUR
            }
        val rightUpValid =
            when {
                dx != DIRECTION_STANDARD && x + (dx * rightUp) in listOf(MIN_X, BOARD_SIZE - 1) -> NOT_FOUR
                dy != DIRECTION_STANDARD && y + (dy * rightUp) in listOf(MIN_Y, BOARD_SIZE - 1) -> NOT_FOUR
                board[y + up][x + right] == OTHER_STONETYPE -> NOT_FOUR
                else -> FIND_FOUR
            }

        return if (leftDownValid + rightUpValid >= FIND_FOUR_STANDARD) FIND_FOUR else NOT_FOUR
    }
}
