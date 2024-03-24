package omok.library

class RenjuRule(
    val board: List<List<Int>>,
) {
    private val directions =
        listOf(
            Pair(1, 0),
            Pair(0, 1),
            Pair(1, 1),
            Pair(1, -1),
        )

    fun isWinCondition(
        x: Int,
        y: Int,
        stoneType: Int,
        checkCount: (Int) -> Boolean,
    ): Boolean {
        return directions.any { direction ->
            checkCount(checkDirection(x, y, stoneType, direction.first, direction.second))
        }
    }

    fun isForbidden(
        x: Int,
        y: Int,
        stoneType: Int,
    ): Boolean {
        return countOpenThrees(x, y) >= MIN_OPEN_THREES || countOpenFours(
            x,
            y,
        ) >= MIN_OPEN_FOURS || isOverLine(x, y, stoneType)
    }

    private fun isOverLine(
        x: Int,
        y: Int,
        stoneType: Int,
    ): Boolean {
        for (direction in directions) {
            if (checkDirection(
                    x,
                    y,
                    stoneType,
                    direction.first,
                    direction.second,
                ) > MAX_STONES_IN_DIRECTION
            ) {
                return true
            }
        }
        return false
    }

    private fun checkDirection(
        x: Int,
        y: Int,
        stoneType: Int,
        dx: Int,
        dy: Int,
    ): Int {
        var maxCount = DEFAULT_COUNT
        var count = DEFAULT_COUNT
        for (i in -4..4) {
            val targetY = y + i * dy
            val targetX = x + i * dx
            if (targetY !in 0 until BOARD_SIZE || targetX !in 0 until BOARD_SIZE) continue

            when (board[targetY][targetX]) {
                stoneType -> {
                    count++
                    maxCount = maxOf(maxCount, count)
                }

                STONE_TYPE_EMPTY -> continue
                else -> count = DEFAULT_COUNT
            }
        }
        return maxCount
    }

    private fun countOpenThrees(
        x: Int,
        y: Int,
    ): Int =
        checkOpenThree(x, y, directions[0].first, directions[0].second) +
            checkOpenThree(x, y, directions[1].first, directions[1].second) +
            checkOpenThree(x, y, directions[2].first, directions[2].second) +
            checkOpenThreeReverse(x, y)

    private fun countOpenFours(
        x: Int,
        y: Int,
    ): Int =
        checkOpenFour(x, y, directions[0].first, directions[0].second) +
            checkOpenFour(x, y, directions[1].first, directions[1].second) +
            checkOpenFour(x, y, directions[2].first, directions[2].second) +
            checkOpenFourReverse(x, y)

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

        val separatedSets = (blink1 == 0 || blink1 == 1) && (blink2 == 0 || blink2 == 1)

        return when {
            stone1 + stone2 != 2 -> 0
            !separatedSets -> 0
            dx != 0 && x.minus(leftDown) in X_Edge -> 0
            dy != 0 && y.minus(leftDown) in Y_Edge -> 0
            dx != 0 && x.plus(rightUp) in X_Edge -> 0
            dy != 0 && y.plus(rightUp) in Y_Edge -> 0
            board[y - dy * (leftDown + 1)][x - dx * (leftDown + 1)] == STONE_TYPE_WHITE -> 0
            board[y + dy * (rightUp + 1)][x + dx * (rightUp + 1)] == STONE_TYPE_WHITE -> 0
            else -> 1
        }
    }

    private fun checkOpenThreeReverse(
        x: Int,
        y: Int,
    ): Int {
        val dx = directions[3].first
        val dy = directions[3].second

        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

        val leftUp = stone1 + blink1
        val rightBottom = stone2 + blink2

        val separatedSets = (blink1 == 0 || blink1 == 1) && (blink2 == 0 || blink2 == 1)

        return when {
            stone1 + stone2 != 2 -> 0
            !separatedSets -> 0
            dx != 0 && x.minus(leftUp) in X_Edge -> 0
            dy != 0 && y.plus(leftUp) in Y_Edge -> 0
            dx != 0 && x.plus(rightBottom) in X_Edge -> 0
            dy != 0 && y.minus(rightBottom) in Y_Edge -> 0
            board[y - rightBottom - 1][x + rightBottom + 1] == STONE_TYPE_WHITE -> 0
            board[y + leftUp + 1][x - leftUp - 1] == STONE_TYPE_WHITE -> 0
            else -> 1
        }
    }

    private fun checkOpenFour(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
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

        val leftDownValid =
            when {
                dx != 0 && x.minus(dx * leftDown) in X_Edge -> 0
                dy != 0 && y.minus(dy * leftDown) in Y_Edge -> 0
                board[y - dy * (leftDown + 1)][x - dx * (leftDown + 1)] == STONE_TYPE_WHITE -> 0
                else -> 1
            }
        val rightUpValid =
            when {
                dx != 0 && x.plus(dx * rightUp) in X_Edge -> 0
                dy != 0 && y.plus(dy * rightUp) in Y_Edge -> 0
                board[y + dy * (rightUp + 1)][x + dx * (rightUp + 1)] == STONE_TYPE_WHITE -> 0
                else -> 1
            }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }

    private fun checkOpenFourReverse(
        x: Int,
        y: Int,
    ): Int {
        val dx = directions[3].first
        val dy = directions[3].second

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

        val leftUpValid =
            when {
                dx != 0 && x.minus(leftUp) in X_Edge -> 0
                dy != 0 && y.plus(leftUp) in Y_Edge -> 0
                board[y - rightBottom - 1][x + rightBottom + 1] == STONE_TYPE_WHITE -> 0
                else -> 1
            }

        val rightBottomValid =
            when {
                dx != 0 && x.plus(rightBottom) in X_Edge -> 0
                dy != 0 && y.minus(rightBottom) in Y_Edge -> 0
                board[y + leftUp + 1][x - leftUp - 1] == STONE_TYPE_WHITE -> 0
                else -> 1
            }

        return if (leftUpValid + rightBottomValid >= 1) 1 else 0
    }

    private fun search(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Pair<Int, Int> {
        var toRight = x
        var toTop = y
        var stoneCount = 0
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
                STONE_TYPE_BLACK -> {
                    stoneCount++
                    blink = blinkCount
                }

                STONE_TYPE_WHITE -> break
                STONE_TYPE_EMPTY -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }
            }
        }
        return Pair(stoneCount, blink)
    }

    companion object {
        private const val MIN_OPEN_THREES = 2
        private const val MIN_OPEN_FOURS = 2
        private const val MIN_X = 0
        private const val MAX_X = 14
        private const val MIN_Y = 0
        private const val MAX_Y = 14
        private const val MAX_STONES_IN_DIRECTION = 5
        private const val STONE_TYPE_EMPTY = 0
        private const val STONE_TYPE_BLACK = 1
        private const val STONE_TYPE_WHITE = 2
        private const val DEFAULT_COUNT = 1
        private const val BOARD_SIZE = 15
        private val X_Edge = listOf(MIN_X, MAX_X)
        private val Y_Edge = listOf(MIN_Y, MAX_Y)
    }
}
