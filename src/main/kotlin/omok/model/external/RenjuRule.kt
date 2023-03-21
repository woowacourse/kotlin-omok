package omok.model.external

class RenjuRule(
    private val board: List<List<Int>>,
    private val currentStone: Int = BLACK_STONE,
    private val otherStone: Int = WHITE_STONE,
) {
    private val directions = listOf(listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(1, -1))

    fun countOpenThrees(x: Int, y: Int): Int =
        directions.sumOf { direction -> checkOpenThree(x, y, direction[0], direction[1]) }

    fun countOpenFours(x: Int, y: Int): Int =
        directions.sumOf { direction -> checkOpenFour(x, y, direction[0], direction[1]) }

    fun validateWhiteWin(x: Int, y: Int): Boolean =
        directions.map { direction -> checkWhiteWin(x, y, direction[0], direction[1]) }.contains(true)

    fun validateBlackWin(x: Int, y: Int): Boolean =
        directions.map { direction -> checkBlackWin(x, y, direction[0], direction[1]) }.contains(true)

    private fun checkOpenThree(x: Int, y: Int, dx: Int, dy: Int): Int {
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
            dx != 0 && x - leftDown in X_Edge -> 0
            dy != 0 && y - leftDown in Y_Edge -> 0
            dx != 0 && x + rightUp in X_Edge -> 0
            dy != 0 && y + rightUp in Y_Edge -> 0
            board[y - down ][x - left] == otherStone -> 0
            board[y + up][x + right] == otherStone -> 0
            countToWall(x, y, -dx, -dy) + countToWall(x, y, dx, dy) <= 5 -> 0
            else -> 1
        }
    }

    private fun countToWall(x: Int, y: Int, dx: Int, dy: Int): Int {
        var toRight = x
        var toTop = y
        var distance = 0
        while (true) {
            if (dx > 0 && toRight == MAX_X) break
            if (dx < 0 && toRight == MIN_X) break
            if (dy > 0 && toTop == MAX_Y) break
            if (dy < 0 && toTop == MIN_X) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                in listOf(currentStone, EMPTY_STONE) -> distance++
                otherStone -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }

    private fun checkOpenFour(x: Int, y: Int, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

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

        val leftDownValid = when {
            dx != 0 && x - dx * leftDown in X_Edge -> 0
            dy != 0 && y - dy * leftDown in Y_Edge -> 0
            board[y - down][x - left] == otherStone -> 0
            else -> 1
        }
        val rightUpValid = when {
            dx != 0 && x + (dx * rightUp) in X_Edge -> 0
            dy != 0 && y + (dy * rightUp) in Y_Edge -> 0
            board[y + up][x + right] == otherStone -> 0
            else -> 1
        }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }

    private fun checkBlackWin(x: Int, y: Int, dx: Int, dy: Int): Boolean {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 == 4 -> true
            else -> false
        }
    }

    private fun checkWhiteWin(x: Int, y: Int, dx: Int, dy: Int): Boolean {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 >= 4 -> true
            else -> false
        }
    }

    private fun search(x: Int, y: Int, dx: Int, dy: Int): Pair<Int, Int> {
        var toRight = x
        var toTop = y
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (dx > 0 && toRight == MAX_X) break
            if (dx < 0 && toRight == MIN_X) break
            if (dy > 0 && toTop == MAX_Y) break
            if (dy < 0 && toTop == MIN_X) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                currentStone -> {
                    stone++
                    blink = blinkCount
                }
                otherStone -> break
                EMPTY_STONE -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }
                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }

    companion object {
        private const val EMPTY_STONE = 0
        private const val BLACK_STONE = 1
        private const val WHITE_STONE = 2
        private const val MIN_X = 0
        private const val MAX_X = 14
        private const val MIN_Y = 0
        private const val MAX_Y = 14
        private val X_Edge = listOf(MIN_X, MAX_X)
        private val Y_Edge = listOf(MIN_Y, MAX_Y)
    }
}
