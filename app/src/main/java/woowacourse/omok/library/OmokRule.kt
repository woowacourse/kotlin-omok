package omok.library

class OmokRule(
    val currentStone: Int = BLACK_STONE,
    val otherStone: Int = WHITE_STONE,
    val board: List<List<Int>>,
    val boardSize: Int = BOARD_SIZE,
) {
    private val directions =
        listOf(
            Pair(1, 0),
            Pair(1, 1),
            Pair(0, 1),
            Pair(1, -1),
        )

    fun isForbiddenUtilizable(): Boolean = currentStone == BLACK_STONE

    fun isForbidden(i: Int, j: Int): Boolean {
        if(!isForbiddenUtilizable()) return false
        return isThreeThree(i, j) || isFourFour(i, j) || isMoreThanFive(i, j)
    }

    fun isThreeThree(
        x: Int,
        y: Int,
    ): Boolean {
        return directions.sumOf { checkOpenThree(y, x, it.first, it.second) } >= 2
    }

    fun isFourFour(
        x: Int,
        y: Int,
    ): Boolean {
        return directions.sumOf { checkOpenFour(y, x, it.first, it.second) } >= 2
    }

    fun isMoreThanFive(
        x: Int,
        y: Int,
    ): Boolean {
        return directions.map { direction -> checkMoreThanFive(y, x, direction.first, direction.second) }
            .contains(true)
    }

    private fun isOmok(
        x: Int,
        y: Int,
    ): Boolean {
        return directions.map { direction -> checkOmok(y, x, direction.first, direction.second) }.contains(true)
    }

    fun isWin(
        x: Int,
        y: Int,
    ): Boolean {
        return if (isForbiddenUtilizable()) isOmok(x, y) && !isMoreThanFive(x, y) else isOmok(x, y)
    }

    private fun checkOpenThree(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
        val (stone1, blink1) = search(y, x, -dx, -dy)
        val (stone2, blink2) = search(y, x, dx, dy)

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
            countToWall(y, x, -dx, -dy) + countToWall(y, x, dx, dy) <= 5 -> 0
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
            if (dx > 0 && toRight == BOARD_SIZE - 1) break
            if (dx < 0 && toRight == MIN_X) break
            if (dy > 0 && toTop == BOARD_SIZE - 1) break
            if (dy < 0 && toTop == MIN_X) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                otherStone -> break
                else -> distance++
            }
        }
        return distance
    }

    private fun checkOpenFour(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
        val (stone1, blink1) = search(y, x, -dx, -dy)
        val (stone2, blink2) = search(y, x, dx, dy)

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
    ): Boolean {
        val (stone1, blink1) = search(y, x, -dx, -dy)
        val (stone2, blink2) = search(y, x, dx, dy)

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
    ): Boolean {
        val (stone1, blink1) = search(y, x, -dx, -dy)
        val (stone2, blink2) = search(y, x, dx, dy)

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
                else -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }
//                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }

    companion object {
        private const val BLACK_STONE = 1
        private const val WHITE_STONE = 2
        private const val BOARD_SIZE: Int = 15
        private const val MIN_X = 0
        private const val MIN_Y = 0
    }
}
