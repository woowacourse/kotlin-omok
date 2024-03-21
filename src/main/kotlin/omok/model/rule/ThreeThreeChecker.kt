package omok.model.rule

import omok.model.board.Board

object ThreeThreeChecker : OmokRule(Board.board) {
    fun checkThreeThree(
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
        val left = dx * (leftDown + 1)
        val down = dy * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = dx * (rightUp + 1)
        val up = dy * (rightUp + 1)

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && x - leftDown in listOf(MIN_X, Board.BOARD_SIZE - 1) -> 0
            dy != 0 && y - leftDown in listOf(MIN_Y, Board.BOARD_SIZE - 1) -> 0
            dx != 0 && x + rightUp in listOf(MIN_X, Board.BOARD_SIZE - 1) -> 0
            dy != 0 && y + rightUp in listOf(MIN_Y, Board.BOARD_SIZE - 1) -> 0
            Board.board[y - down][x - left] == OTHER_STONE -> 0
            Board.board[y + up][x + right] == OTHER_STONE -> 0
            countToWall(x, y, -dx, -dy) + countToWall(x, y, dx, dy) <= 5 -> 0
            else -> 1
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
            if (dx > 0 && toRight == Board.BOARD_SIZE - 1) break
            if (dx < 0 && toRight == MIN_X) break
            if (dy > 0 && toTop == Board.BOARD_SIZE - 1) break
            if (dy < 0 && toTop == MIN_X) break
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
