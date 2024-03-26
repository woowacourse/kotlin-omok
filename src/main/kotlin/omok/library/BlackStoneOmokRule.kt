package omok.library

import omok.model.PositionType

object BlackStoneOmokRule : OmokRule {
    private val currentStone: PositionType = PositionType.BLACK_STONE
    private val otherStone: PositionType = PositionType.WHITE_STONE
    private const val BOARD_SIZE: Int = 15
    private const val MIN_X = 0
    private const val MIN_Y = 0

    private val directions =
        listOf(
            Pair(1, 0),
            Pair(1, 1),
            Pair(0, 1),
            Pair(1, -1),
        )

    fun isThreeThree(
        x: Int,
        y: Int,
        board: Array<Array<PositionType>>,
    ): Boolean {
        return directions.sumOf { checkOpenThree(y, x, it.first, it.second, board) } >= 2
    }

    fun isFourFour(
        x: Int,
        y: Int,
        board: Array<Array<PositionType>>,
    ): Boolean {
        return directions.sumOf { checkOpenFour(y, x, it.first, it.second, board) } >= 2
    }

    fun isMoreThanFive(
        x: Int,
        y: Int,
        board: Array<Array<PositionType>>,
    ): Boolean {
        return directions.map { direction -> checkMoreThanFive(y, x, direction.first, direction.second, board) }
            .contains(true)
    }

    override fun isOmok(
        x: Int,
        y: Int,
        board: Array<Array<PositionType>>,
    ): Boolean {
        return directions.map { direction -> checkOmok(y, x, direction.first, direction.second, board) }.contains(true)
    }

    private fun checkOpenThree(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
        board: Array<Array<PositionType>>,
    ): Int {
        val (stone1, blink1) = search(y, x, -dx, -dy, board)
        val (stone2, blink2) = search(y, x, dx, dy, board)

        val leftDown = stone1 + blink1
        val left = dx * (leftDown + 1)
        val down = dy * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = dx * (rightUp + 1)
        val up = dy * (rightUp + 1)

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && x - leftDown in listOf(MIN_X, BOARD_SIZE - 1) -> 0
            dy != 0 && y - leftDown in listOf(MIN_Y, BOARD_SIZE - 1) -> 0
            dx != 0 && x + rightUp in listOf(MIN_X, BOARD_SIZE - 1) -> 0
            dy != 0 && y + rightUp in listOf(MIN_Y, BOARD_SIZE - 1) -> 0
            board[y - down][x - left] == otherStone -> 0
            board[y + up][x + right] == otherStone -> 0
            countToWall(y, x, -dx, -dy, board) + countToWall(y, x, dx, dy, board) <= 5 -> 0
            else -> 1
        }
    }

    private fun countToWall(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
        board: Array<Array<PositionType>>,
    ): Int {
        var toRight = x
        var toTop = y
        var distance = 0
        while (true) {
            if (dx > 0 && toRight == BOARD_SIZE - 1) break
            if (dx < 0 && toRight == MIN_X) break
            if (dy > 0 && toTop == BOARD_SIZE - 1) break
            if (dy < 0 && toTop == MIN_X) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                in listOf(currentStone, PositionType.EMPTY, PositionType.BLOCK) -> distance++
                otherStone -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }

    private fun checkOpenFour(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
        board: Array<Array<PositionType>>,
    ): Int {
        val (stone1, blink1) = search(y, x, -dx, -dy, board)
        val (stone2, blink2) = search(y, x, dx, dy, board)

        val leftDown = stone1 + blink1
        val left = dx * (leftDown + 1)
        val down = dy * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = dx * (rightUp + 1)
        val up = dy * (rightUp + 1)

        when {
            blink1 + blink2 == 2 && stone1 + stone2 == 4 -> return 2
            blink1 + blink2 == 2 && stone1 + stone2 == 5 -> return 2
            stone1 + stone2 != 3 -> return 0
            blink1 + blink2 == 2 -> return 0
        }

        val leftDownValid =
            when {
                dx != 0 && x - dx * leftDown in listOf(MIN_X, BOARD_SIZE - 1) -> 0
                dy != 0 && y - dy * leftDown in listOf(MIN_Y, BOARD_SIZE - 1) -> 0
                board[y - down][x - left] == otherStone -> 0
                else -> 1
            }
        val rightUpValid =
            when {
                dx != 0 && x + (dx * rightUp) in listOf(MIN_X, BOARD_SIZE - 1) -> 0
                dy != 0 && y + (dy * rightUp) in listOf(MIN_Y, BOARD_SIZE - 1) -> 0
                board[y + up][x + right] == otherStone -> 0
                else -> 1
            }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }

    private fun checkMoreThanFive(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
        board: Array<Array<PositionType>>,
    ): Boolean {
        val (stone1, blink1) = search(y, x, -dx, -dy, board)
        val (stone2, blink2) = search(y, x, dx, dy, board)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 > 4 -> true
            else -> false
        }
    }

    private fun checkOmok(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
        board: Array<Array<PositionType>>,
    ): Boolean {
        val (stone1, blink1) = search(y, x, -dx, -dy, board)
        val (stone2, blink2) = search(y, x, dx, dy, board)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 == 4 -> true
            else -> false
        }
    }

    private fun search(
        y: Int,
        x: Int,
        dx: Int,
        dy: Int,
        board: Array<Array<PositionType>>,
    ): Pair<Int, Int> {
        var toRight = x
        var toTop = y
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (dx > 0 && toRight == BOARD_SIZE - 1) break
            if (dx < 0 && toRight == MIN_X) break
            if (dy > 0 && toTop == BOARD_SIZE - 1) break
            if (dy < 0 && toTop == MIN_X) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                currentStone -> {
                    stone++
                    blink = blinkCount
                }

                otherStone -> break
                PositionType.EMPTY, PositionType.BLOCK -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }

                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }
}
