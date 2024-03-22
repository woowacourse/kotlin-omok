package omok.rule

import omok.model.Board
import omok.model.Stone
import omok.model.StoneType

object RenjuRule {
    private const val MIN_OPEN_THREES = 2
    private const val MIN_OPEN_FOURS = 2
    private const val MIN_X = 0
    private const val MAX_X = 14
    private const val MIN_Y = 0
    private const val MAX_Y = 14
    private const val MAX_STONES_IN_DIRECTION = 5
    private val X_Edge = listOf(MIN_X, MAX_X)
    private val Y_Edge = listOf(MIN_Y, MAX_Y)

    fun isForbidden(
        board: Board,
        stone: Stone,
    ): Boolean {
        return countOpenThrees(board, stone) >= MIN_OPEN_THREES ||
                countOpenFours(board, stone) >= MIN_OPEN_FOURS ||
                isOverLine(board, stone)
    }

    private fun isOverLine(
        board: Board,
        stone: Stone,
    ): Boolean {
        val y = stone.point.y
        val x = stone.point.x
        val stoneType = stone.type
        for (direction in Direction.entries) {
            if (BlackRule.checkDirection(
                    board,
                    y,
                    x,
                    stoneType,
                    direction.dx,
                    direction.dy,
                ) > MAX_STONES_IN_DIRECTION
            ) {
                return true
            }
        }
        return false
    }

    private fun countOpenThrees(
        board: Board,
        stone: Stone,
    ): Int =
        checkOpenThree(board, stone, Direction.HORIZONTAL) +
                checkOpenThree(board, stone, Direction.DIAGONAL_UP) +
                checkOpenThree(board, stone, Direction.VERTICAL) +
                checkOpenThreeReverse(board, stone, Direction.DIAGONAL_DOWN)

    private fun countOpenFours(
        board: Board,
        stone: Stone,
    ): Int =
        checkOpenFour(board, stone, Direction.HORIZONTAL) +
                checkOpenFour(board, stone, Direction.DIAGONAL_UP) +
                checkOpenFour(board, stone, Direction.VERTICAL) +
                checkOpenFourReverse(board, stone, Direction.DIAGONAL_DOWN)

    private fun checkOpenThree(
        board: Board,
        stone: Stone,
        direction: Direction,
    ): Int {
        val dx = direction.dx
        val dy = direction.dy
        val (stone1, blink1) = search(board, stone, -dx, -dy)
        val (stone2, blink2) = search(board, stone, dx, dy)

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        val separatedSets = (blink1 == 0 || blink1 == 1) && (blink2 == 0 || blink2 == 1)

        return when {
            stone1 + stone2 != 2 -> 0
            !separatedSets -> 0
            dx != 0 && stone.point.x.minus(leftDown) in X_Edge -> 0
            dy != 0 && stone.point.y.minus(leftDown) in Y_Edge -> 0
            dx != 0 && stone.point.x.plus(rightUp) in X_Edge -> 0
            dy != 0 && stone.point.y.plus(rightUp) in Y_Edge -> 0
            board.table[stone.point.y - dy * (leftDown + 1)][stone.point.x - dx * (leftDown + 1)] == StoneType.WHITE -> 0
            board.table[stone.point.y + dy * (rightUp + 1)][stone.point.x + dx * (rightUp + 1)] == StoneType.WHITE -> 0
            else -> 1
        }
    }

    private fun checkOpenThreeReverse(
        board: Board,
        stone: Stone,
        direction: Direction,
    ): Int {
        val dx = direction.dx
        val dy = direction.dy

        val (stone1, blink1) = search(board, stone, -dx, -dy)
        val (stone2, blink2) = search(board, stone, dx, dy)

        val leftUp = stone1 + blink1
        val rightBottom = stone2 + blink2

        val separatedSets = (blink1 == 0 || blink1 == 1) && (blink2 == 0 || blink2 == 1)

        return when {
            stone1 + stone2 != 2 -> 0
            !separatedSets -> 0
            dx != 0 && stone.point.x.minus(leftUp) in X_Edge -> 0
            dy != 0 && stone.point.y.plus(leftUp) in Y_Edge -> 0
            dx != 0 && stone.point.x.plus(rightBottom) in X_Edge -> 0
            dy != 0 && stone.point.y.minus(rightBottom) in Y_Edge -> 0
            board.table[stone.point.y - rightBottom - 1][stone.point.x + rightBottom + 1] == StoneType.WHITE -> 0
            board.table[stone.point.y + leftUp + 1][stone.point.x - leftUp - 1] == StoneType.WHITE -> 0
            else -> 1
        }
    }

    private fun checkOpenFour(
        board: Board,
        stone: Stone,
        direction: Direction,
    ): Int {
        val dx = direction.dx
        val dy = direction.dy

        val (stone1, blink1) = search(board, stone, -dx, -dy)
        val (stone2, blink2) = search(board, stone, dx, dy)

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
                dx != 0 && stone.point.x.minus(dx * leftDown) in X_Edge -> 0
                dy != 0 && stone.point.y.minus(dy * leftDown) in Y_Edge -> 0
                board.table[stone.point.y - dy * (leftDown + 1)][stone.point.x - dx * (leftDown + 1)] == StoneType.WHITE -> 0
                else -> 1
            }
        val rightUpValid =
            when {
                dx != 0 && stone.point.x.plus(dx * rightUp) in X_Edge -> 0
                dy != 0 && stone.point.y.plus(dy * rightUp) in Y_Edge -> 0
                board.table[stone.point.y + dy * (rightUp + 1)][stone.point.x + dx * (rightUp + 1)] == StoneType.WHITE -> 0
                else -> 1
            }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }

    private fun checkOpenFourReverse(
        board: Board,
        stone: Stone,
        direction: Direction,
    ): Int {
        val dx = direction.dx
        val dy = direction.dy

        val (stone1, blink1) = search(board, stone, -dx, -dy)
        val (stone2, blink2) = search(board, stone, dx, dy)

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
                dx != 0 && stone.point.x.minus(leftUp) in X_Edge -> 0
                dy != 0 && stone.point.y.plus(leftUp) in Y_Edge -> 0
                board.table[stone.point.y - rightBottom - 1][stone.point.x + rightBottom + 1] == StoneType.WHITE -> 0
                else -> 1
            }

        val rightBottomValid =
            when {
                dx != 0 && stone.point.x.plus(rightBottom) in X_Edge -> 0
                dy != 0 && stone.point.y.minus(rightBottom) in Y_Edge -> 0
                board.table[stone.point.y + leftUp + 1][stone.point.x - leftUp - 1] == StoneType.WHITE -> 0
                else -> 1
            }

        return if (leftUpValid + rightBottomValid >= 1) 1 else 0
    }

    private fun search(
        board: Board,
        stone: Stone,
        dx: Int,
        dy: Int,
    ): Pair<Int, Int> {
        var toRight = stone.point.x
        var toTop = stone.point.y
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
            when (board.table[toTop][toRight]) {
                StoneType.BLACK -> {
                    stoneCount++
                    blink = blinkCount
                }

                StoneType.WHITE -> break
                StoneType.EMPTY -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }
            }
        }
        return Pair(stoneCount, blink)
    }
}
