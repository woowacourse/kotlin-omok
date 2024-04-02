package lib.renjurule

import omok.model.result.PutResult

object ThreeThreeChecker : OmokRule() {
    private const val NOT_THREE = 0
    private const val FIND_THREE = 1
    private const val INVALID_THREE_THREE_STONE_COUNT = 2
    private const val THREE_THREE_STANDARD_COUNT = 2
    private const val COUNT_TO_WALL_ALLOWANCE = 5

    override fun check(
        board: Array<Array<Int>>,
        x: Int,
        y: Int,
    ): PutResult =
        if (directions.sumOf { direction ->
                checkOpenThree(board, x, y, direction[0], direction[1])
            } >= THREE_THREE_STANDARD_COUNT
        ) {
            PutResult.DoubleThree
        } else {
            PutResult.Running
        }

    private fun checkOpenThree(
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

        return when {
            stone1 + stone2 != INVALID_THREE_THREE_STONE_COUNT -> NOT_THREE
            blink1 + blink2 == INVALID_BLANK_ALLOWANCE -> INVALID_BLANK_ALLOWANCE
            dx != DIRECTION_STANDARD && x - leftDown in listOf(MIN_X, BOARD_SIZE - 1) -> NOT_THREE
            dy != DIRECTION_STANDARD && y - leftDown in listOf(MIN_Y, BOARD_SIZE - 1) -> NOT_THREE
            dx != DIRECTION_STANDARD && x + rightUp in listOf(MIN_X, BOARD_SIZE - 1) -> NOT_THREE
            dy != DIRECTION_STANDARD && y + rightUp in listOf(MIN_Y, BOARD_SIZE - 1) -> NOT_THREE
            board[y - down][x - left] == OTHER_STONETYPE -> NOT_THREE
            board[y + up][x + right] == OTHER_STONETYPE -> NOT_THREE
            countToWall(board, x, y, -dx, -dy) +
                countToWall(board, x, y, dx, dy) <= COUNT_TO_WALL_ALLOWANCE -> NOT_THREE

            else -> FIND_THREE
        }
    }

    private fun countToWall(
        board: Array<Array<Int>>,
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
        var toRight = x
        var toTop = y
        var distance = DEFAULT_COUNT
        while (true) {
            if (dx > DIRECTION_STANDARD && toRight == BOARD_SIZE - 1) break
            if (dx < DIRECTION_STANDARD && toRight == MIN_X) break
            if (dy > DIRECTION_STANDARD && toTop == BOARD_SIZE - 1) break
            if (dy < DIRECTION_STANDARD && toTop == MIN_X) break

            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                in listOf(CURRENT_STONETYPE, EMPTY_STONETYPE) -> distance++
                OTHER_STONETYPE -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }
}
