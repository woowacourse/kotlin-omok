package model.domain.rule

import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

class OmokForbiddenRule(board: Board, currentStone: Stone) {

    private val convertBoard: List<List<Int>> =
        board.system.values.map { line ->
            line.map { getStoneNumber(it) }
        }

    private val stoneState: Int = when (currentStone) {
        Stone.BLACK -> BLACK_STONE
        Stone.WHITE -> WHITE_STONE
        Stone.EMPTY -> EMPTY_STONE
    }

    private val nextStone: Int = when (stoneState) {
        1 -> 2
        2 -> 1
        else -> 0
    }

    fun isForbidden(location: Location): Boolean {
        val x = location.coordinationY.value
        val y = location.coordinationX.value
        return countOpenFours(x, y) >= 2 || countOpenThrees(x, y) >= 2
    }

    private fun getStoneNumber(stone: Stone): Int = when (stone) {
        Stone.BLACK -> BLACK_STONE
        Stone.WHITE -> WHITE_STONE
        Stone.EMPTY -> EMPTY_STONE
    }

    private fun countOpenThrees(x: Int, y: Int): Int =
        checkOpenThree(x, y, 1, 0) +
            checkOpenThree(x, y, 1, 1) +
            checkOpenThree(x, y, 0, 1) +
            checkOpenThreeReverse(x, y, 1, -1)

    private fun countOpenFours(x: Int, y: Int): Int =
        checkOpenFour(x, y, 1, 0) +
            checkOpenFour(x, y, 1, 1) +
            checkOpenFour(x, y, 0, 1) +
            checkOpenFourReverse(x, y, 1, -1)

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
            convertBoard[y - dy * (leftDown + 1)][x - dx * (leftDown + 1)] == nextStone -> 0
            convertBoard[y + dy * (rightUp + 1)][x + dx * (rightUp + 1)] == nextStone -> 0
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
            convertBoard[y - rightBottom - 1][x + rightBottom + 1] == nextStone -> 0
            convertBoard[y + leftUp + 1][x - leftUp - 1] == nextStone -> 0
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
            convertBoard[y - dy * (leftDown + 1)][x - dx * (leftDown + 1)] == nextStone -> 0
            else -> 1
        }
        val rightUpValid = when {
            dx != 0 && x.plus(dx * rightUp) in X_Edge -> 0
            dy != 0 && y.plus(dy * rightUp) in Y_Edge -> 0
            convertBoard[y + dy * (rightUp + 1)][x + dx * (rightUp + 1)] == nextStone -> 0
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
            convertBoard[y - rightBottom - 1][x + rightBottom + 1] == nextStone -> 0
            else -> 1
        }

        val rightBottomValid = when {
            dx != 0 && x.plus(rightBottom) in X_Edge -> 0
            dy != 0 && y.minus(rightBottom) in Y_Edge -> 0
            convertBoard[y + leftUp + 1][x - leftUp - 1] == nextStone -> 0
            else -> 1
        }

        return if (leftUpValid + rightBottomValid >= 1) 1 else 0
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
            when (convertBoard[toTop][toRight]) {
                stoneState -> {
                    stone++
                    blink = blinkCount
                }

                nextStone -> break
                EMPTY_STONE -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }

                else -> {
                    println("convertBoard[toTop][toRight] = ${convertBoard[toTop][toRight]}")
                    println("stone = $stoneState")
                    throw IllegalArgumentException()
                }
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
