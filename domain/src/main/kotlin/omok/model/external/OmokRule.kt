package omok.model.external

class OmokRule(
    private val board: List<List<Int>>,
    private val currentStone: Int = BLACK_STONE,
    private val otherStone: Int = WHITE_STONE,
) {
    fun countOpenThrees(x: Int, y: Int): Int =
        checkOpenThree(x, y, 1, 0) +
            checkOpenThree(x, y, 1, 1) +
            checkOpenThree(x, y, 0, 1) +
            checkOpenThreeReverse(x, y, 1, -1)

    fun countOpenFours(x: Int, y: Int): Int =
        checkOpenFour(x, y, 1, 0) +
            checkOpenFour(x, y, 1, 1) +
            checkOpenFour(x, y, 0, 1) +
            checkOpenFourReverse(x, y, 1, -1)

    fun validateWhiteWin(x: Int, y: Int): Boolean =
        checkWhiteWin(x, y, 1, 0) ||
            checkWhiteWin(x, y, 1, 1) ||
            checkWhiteWin(x, y, 0, 1) ||
            checkWhiteWin(x, y, -1, 1)

    fun validateBlackWin(x: Int, y: Int): Boolean =
        checkBlackWin(x, y, 1, 0) ||
            checkBlackWin(x, y, 1, 1) ||
            checkBlackWin(x, y, 0, 1) ||
            checkBlackWin(x, y, -1, 1)

    private fun checkOpenThree(x: Int, y: Int, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && x.minus(leftDown) in X_Edge -> 0
            dy != 0 && y.minus(leftDown) in Y_Edge -> 0
            dx != 0 && x.plus(rightUp) in X_Edge -> 0
            dy != 0 && y.plus(rightUp) in Y_Edge -> 0
            board[y - dy * (leftDown + 1)][x - dx * (leftDown + 1)] == otherStone -> 0
            board[y + dy * (rightUp + 1)][x + dx * (rightUp + 1)] == otherStone -> 0
            blink1 + blink2 == 0 ->
                when {
                    dx != 0 && x.minus(leftDown + 1) in X_Edge -> 0
                    dy != 0 && y.minus(leftDown + 1) in Y_Edge -> 0
                    dx != 0 && x.plus(rightUp + 1) in X_Edge -> 0
                    dy != 0 && y.plus(rightUp + 1) in Y_Edge -> 0
                    board[y - dy * (leftDown + 2)][x - dx * (leftDown + 2)] == otherStone && board[y + dy * (rightUp + 2)][x + dx * (rightUp + 2)] == otherStone -> 0
                    else -> 1
                }

            else -> 1
        }
    }

    private fun checkOpenThreeReverse(x: Int, y: Int, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

        val leftUp = stone1 + blink1
        val rightBottom = stone2 + blink2

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && x.minus(leftUp) in X_Edge -> 0
            dy != 0 && y.plus(leftUp) in Y_Edge -> 0
            dx != 0 && x.plus(rightBottom) in X_Edge -> 0
            dy != 0 && y.minus(rightBottom) in Y_Edge -> 0
            board[y - rightBottom - 1][x + rightBottom + 1] == otherStone -> 0
            board[y + leftUp + 1][x - leftUp - 1] == otherStone -> 0
            else -> 1
        }
    }

    private fun checkOpenFour(x: Int, y: Int, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        when {
            blink1 + blink2 == 2 && stone1 + stone2 == 4 -> return 2
            blink1 + blink2 == 2 && stone1 + stone2 == 5 -> return 2
            stone1 + stone2 != 3 -> return 0
            blink1 + blink2 == 2 -> return 0
        }

        val leftDownValid = when {
            dx != 0 && x.minus(dx * leftDown) in X_Edge -> 0
            dy != 0 && y.minus(dy * leftDown) in Y_Edge -> 0
            board[y - dy * (leftDown + 1)][x - dx * (leftDown + 1)] == otherStone -> 0
            else -> 1
        }
        val rightUpValid = when {
            dx != 0 && x.plus(dx * rightUp) in X_Edge -> 0
            dy != 0 && y.plus(dy * rightUp) in Y_Edge -> 0
            board[y + dy * (rightUp + 1)][x + dx * (rightUp + 1)] == otherStone -> 0
            else -> 1
        }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }

    private fun checkOpenFourReverse(x: Int, y: Int, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

        val leftUp = stone1 + blink1
        val rightBottom = stone2 + blink2

        when {
            blink1 + blink2 == 2 && stone1 + stone2 == 5 -> return 2
            blink1 + blink2 == 2 && stone1 + stone2 == 4 -> return 2
            stone1 + stone2 != 3 -> return 0
            blink1 + blink2 == 2 -> return 0
        }

        val leftUpValid = when {
            dx != 0 && x.minus(leftUp) in X_Edge -> 0
            dy != 0 && y.plus(leftUp) in Y_Edge -> 0
            board[y - rightBottom - 1][x + rightBottom + 1] == otherStone -> 0
            else -> 1
        }

        val rightBottomValid = when {
            dx != 0 && x.plus(rightBottom) in X_Edge -> 0
            dy != 0 && y.minus(rightBottom) in Y_Edge -> 0
            board[y + leftUp + 1][x - leftUp - 1] == otherStone -> 0
            else -> 1
        }

        return if (leftUpValid + rightBottomValid >= 1) 1 else 0
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
