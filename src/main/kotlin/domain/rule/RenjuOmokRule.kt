package domain.rule

import domain.stone.Board.Companion.BOARD_SIZE
import domain.stone.Stone
import domain.stone.StoneType

class RenjuOmokRule: OmokRule {

    companion object {
        const val MIN_OPEN_THREES = 2
        const val MIN_OPEN_FOURS = 2
        private const val MIN_X = 1
        private const val MAX_X = 15
        private const val MIN_Y = 1
        private const val MAX_Y = 15
        private val X_Edge = listOf(MIN_X, MAX_X)
        private val Y_Edge = listOf(MIN_Y, MAX_Y)
    }

    override fun isForbidden(board: List<List<StoneType>>, stone: Stone): Boolean {
        return countOpenThrees(board, stone) >= MIN_OPEN_THREES ||
                countOpenFours(board, stone) >= MIN_OPEN_FOURS
    }

    private fun countOpenThrees(board: List<List<StoneType>>, stone: Stone): Int =
        checkOpenThree(board, stone, 1, 0) +
            checkOpenThree(board, stone, 1, 1) +
            checkOpenThree(board, stone, 0, 1) +
            checkOpenThreeReverse(board, stone, 1, -1)

    private fun countOpenFours(board: List<List<StoneType>>, stone: Stone): Int =
        checkOpenFour(board, stone, 1, 0) +
            checkOpenFour(board, stone, 1, 1) +
            checkOpenFour(board, stone, 0, 1) +
            checkOpenFourReverse(board, stone, 1, -1)

    private fun checkOpenThree(board: List<List<StoneType>>, stone: Stone, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(board, stone, -dx, -dy)
        val (stone2, blink2) = search(board, stone, dx, dy)

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && stone.position.x.minus(leftDown) in X_Edge -> 0
            dy != 0 && stone.position.y.minus(leftDown) in Y_Edge -> 0
            dx != 0 && stone.position.x.plus(rightUp) in X_Edge -> 0
            dy != 0 && stone.position.y.plus(rightUp) in Y_Edge -> 0
            board[stone.position.y - dy * (leftDown + 1)][stone.position.x - dx * (leftDown + 1)] == StoneType.WHITE -> 0
            board[stone.position.y + dy * (rightUp + 1)][stone.position.x + dx * (rightUp + 1)] == StoneType.WHITE -> 0
            else -> 1
        }
    }

    private fun checkOpenThreeReverse(board: List<List<StoneType>>, stone: Stone, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(board, stone, -dx, -dy)
        val (stone2, blink2) = search(board, stone, dx, dy)

        val leftUp = stone1 + blink1
        val rightBottom = stone2 + blink2

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && stone.position.x.minus(leftUp) in X_Edge -> 0
            dy != 0 && stone.position.y.plus(leftUp) in Y_Edge -> 0
            dx != 0 && stone.position.x.plus(rightBottom) in X_Edge -> 0
            dy != 0 && stone.position.y.minus(rightBottom) in Y_Edge -> 0
            board[stone.position.y - rightBottom - 1][stone.position.x + rightBottom + 1] == StoneType.WHITE -> 0
            board[stone.position.y + leftUp + 1][stone.position.x - leftUp - 1] == StoneType.WHITE -> 0
            else -> 1
        }
    }

    private fun checkOpenFour(board: List<List<StoneType>>, stone: Stone, dx: Int, dy: Int): Int {
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

        val leftDownValid = when {
            dx != 0 && stone.position.x.minus(dx * leftDown) in X_Edge -> 0
            dy != 0 && stone.position.y.minus(dy * leftDown) in Y_Edge -> 0
            board[stone.position.y - dy * (leftDown + 1)][stone.position.x - dx * (leftDown + 1)] == StoneType.WHITE -> 0
            else -> 1
        }
        val rightUpValid = when {
            dx != 0 && stone.position.x.plus(dx * rightUp) in X_Edge -> 0
            dy != 0 && stone.position.y.plus(dy * rightUp) in Y_Edge -> 0
            board[stone.position.y + dy * (rightUp + 1)][stone.position.x + dx * (rightUp + 1)] == StoneType.WHITE -> 0
            else -> 1
        }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }

    private fun checkOpenFourReverse(board: List<List<StoneType>>, stone: Stone, dx: Int, dy: Int): Int {
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

        val leftUpValid = when {
            dx != 0 && stone.position.x.minus(leftUp) in X_Edge -> 0
            dy != 0 && stone.position.y.plus(leftUp) in Y_Edge -> 0
            board[stone.position.y - rightBottom - 1][stone.position.x + rightBottom + 1] == StoneType.WHITE -> 0
            else -> 1
        }

        val rightBottomValid = when {
            dx != 0 && stone.position.x.plus(rightBottom) in X_Edge -> 0
            dy != 0 && stone.position.y.minus(rightBottom) in Y_Edge -> 0
            board[stone.position.y + leftUp + 1][stone.position.x - leftUp - 1] == StoneType.WHITE -> 0
            else -> 1
        }

        return if (leftUpValid + rightBottomValid >= 1) 1 else 0
    }

    private fun search(board: List<List<StoneType>>, stone: Stone, dx: Int, dy: Int): Pair<Int, Int> {
        var toRight = stone.position.x
        var toTop = stone.position.y
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

    override fun isWinCondition(board: List<List<StoneType>>, stone: Stone): Boolean {
        if (checkHorizontal(board, stone)) return true
        if (checkVertical(board, stone)) return true
        if (checkDiagonal1(board, stone)) return true
        if (checkDiagonal2(board, stone)) return true
        return false
    }

    private fun checkHorizontal(board: List<List<StoneType>>, stone: Stone): Boolean {
        var count: Int = 0
        val x: Int = stone.position.x
        val y: Int = stone.position.y
        for (i in -4..4) {
            if (x + i !in 1..BOARD_SIZE) continue
            if (board[y][x + i] != stone.type) count = 0
            if (board[y][x + i] == stone.type) {
                count++
                if (count >= 5) break
            }
        }
        return count >= 5
    }

    private fun checkVertical(board: List<List<StoneType>>, stone: Stone): Boolean {
        var count: Int = 0
        val x: Int = stone.position.x
        val y: Int = stone.position.y
        for (i in -4..4) {
            if (y + i !in 1..BOARD_SIZE) continue
            if (board[y + i][x] != stone.type) count = 0
            if (board[y + i][x] == stone.type) {
                count++
                if (count >= 5) break
            }
        }
        return count >= 5
    }

    private fun checkDiagonal1(board: List<List<StoneType>>, stone: Stone): Boolean {
        var count: Int = 0
        val x: Int = stone.position.x
        val y: Int = stone.position.y
        for (i in -4..4) {
            if (x + i !in 1..BOARD_SIZE) continue
            if (y + i !in 1..BOARD_SIZE) continue
            if (board[y + i][x + i] != stone.type) count = 0
            if (board[y + i][x + i] == stone.type) {
                count++
                if (count >= 5) break
            }
        }
        return count >= 5
    }

    private fun checkDiagonal2(board: List<List<StoneType>>, stone: Stone): Boolean {
        var count: Int = 0
        val x: Int = stone.position.x
        val y: Int = stone.position.y
        for (i in -4..4) {
            if (x - i !in 1..BOARD_SIZE) continue
            if (y + i !in 1..BOARD_SIZE) continue
            if (board[y + i][x - i] != stone.type) count = 0
            if (board[y + i][x - i] == stone.type) {
                count++
                if (count >= 5) break
            }
        }
        return count >= 5
    }

}
