package omok.model.rule

import omok.model.board.Board

object DoubleThreeChecker : RenjuRule(Board.board) {
    fun isDoubleThree(
        x: Int,
        y: Int,
    ): Boolean = directions.sumOf { direction -> checkOpenThree(x, y, direction[0], direction[1]) } >= 2

    private fun checkOpenThree(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        return when {
            isValidStoneCount(stone1, stone2) -> 0
            isValidBlinkCount(blink1, blink2) -> 0
            isAtBoardEdge(x, y, dx, dy, leftDown, rightUp) -> 0
            isBesideAnotherStone(x, y, dx, dy, leftDown, rightUp) -> 0
            countToWall(x, y, -dx, -dy) + countToWall(x, y, dx, dy) <= 5 -> 0
            else -> 1
        }
    }

    private fun isValidStoneCount(
        stone1: Int,
        stone2: Int,
    ): Boolean =
        when {
            stone1 + stone2 != 2 -> true
            else -> false
        }

    private fun isValidBlinkCount(
        blink1: Int,
        blink2: Int,
    ): Boolean =
        when {
            blink1 + blink2 == 2 -> true
            else -> false
        }

    private fun isAtBoardEdge(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
        leftDown: Int,
        rightUp: Int,
    ): Boolean {
        return when {
            dx != 0 && x - leftDown in listOf(MIN_X, Board.BOARD_SIZE - 1) -> true
            dy != 0 && y - leftDown in listOf(MIN_Y, Board.BOARD_SIZE - 1) -> true
            dx != 0 && x + rightUp in listOf(MIN_X, Board.BOARD_SIZE - 1) -> true
            dy != 0 && y + rightUp in listOf(MIN_Y, Board.BOARD_SIZE - 1) -> true
            else -> false
        }
    }

    private fun isBesideAnotherStone(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
        leftDown: Int,
        rightUp: Int,
    ): Boolean {
        val left = dx * (leftDown + 1)
        val down = dy * (leftDown + 1)

        val right = dx * (rightUp + 1)
        val up = dy * (rightUp + 1)

        return when (OTHER_STONE) {
            Board.board[y - down][x - left] -> true
            Board.board[y + up][x + right] -> true
            else -> false
        }
    }

    private fun countToWall(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
        var toRight = x
        var toTop = y
        var distance = 0
        while (true) {
            if (isBoardRange(dx, toRight, dy, toTop)) break
            toRight += dx
            toTop += dy
            when (Board.board[toTop][toRight]) {
                in listOf(CURRENT_STONE, EMPTY_STONE) -> distance++
                OTHER_STONE -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }
}
