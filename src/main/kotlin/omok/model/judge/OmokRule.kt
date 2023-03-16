package omok.model.judge

import omok.model.Board
import omok.model.Coordinate
import omok.model.GoStone
import omok.model.state.State
import omok.model.state.Stay
import omok.model.state.Win

class OmokRule(private val board: Board, private val goStone: GoStone) {
    fun checkWin(): State {
        val directions = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 0),
            intArrayOf(1, 1),
            intArrayOf(-1, 1)
        )

        val color = goStone.color
        val lastX = goStone.coordinate.x - 1
        val lastY = goStone.coordinate.y - 1

        for (dir in directions) {
            var count = 1
            var x = lastX
            var y = lastY

            while (x + dir[0] in 0..14 && y + dir[1] >= 0 && y + dir[1] < 15 &&
                board.board[x + dir[0]][y + dir[1]]?.color == color
            ) {
                count++
                x += dir[0]
                y += dir[1]
            }

            x = lastX
            y = lastY

            while (x - dir[0] in 0..14 && y - dir[1] >= 0 && y - dir[1] < 15 &&
                board.board[x - dir[0]][y - dir[1]]?.color == color
            ) {
                count++
                x -= dir[0]
                y -= dir[1]
            }

            if (count >= 5) {
                return Win(board)
            }
        }

        return Stay(board)
    }

    fun countOpenThrees(point: Coordinate): Int =
        checkOpenThree(point, 1, 0) +
            checkOpenThree(point, 1, 1) +
            checkOpenThree(point, 0, 1) +
            checkOpenThreeReverse(point, 1, -1)

    fun countOpenFours(point: Coordinate): Int =
        checkOpenFour(point, 1, 0) +
            checkOpenFour(point, 1, 1) +
            checkOpenFour(point, 0, 1) +
            checkOpenFourReverse(point, 1, -1)

    fun validateWin(point: Coordinate): Boolean =
        checkWin(point, 1, 0) ||
            checkWin(point, 1, 1) ||
            checkWin(point, 0, 1) ||
            checkWin(point, -1, 1)

    private fun Coordinate.isInEdge(gap: Int): Boolean {
        return this.x + gap == 0 || this.x + gap == 14 || this.y + gap == 0 || this.y + gap == 14
    }

    private fun checkOpenThree(point: Coordinate, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(point, -dx, -dy)
        val (stone2, blink2) = search(point, dx, dy)

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && point.x.minus(leftDown) == 0 || point.x.minus(leftDown) == 14 -> 0
            dy != 0 && point.y.minus(leftDown) == 0 || point.y.minus(leftDown) == 14 -> 0
            dx != 0 && point.x.plus(rightUp) == 0 || point.x.plus(rightUp) == 14 -> 0
            dx != 0 && point.y.plus(rightUp) == 0 || point.y.plus(rightUp) == 14 -> 0

            board.board[point.y - dy * (leftDown + 1)][point.x - dx * (leftDown + 1)]?.color != goStone.color -> 0
            board.board[point.y + dy * (rightUp + 1)][point.x + dx * (rightUp + 1)]?.color != goStone.color -> 0
            else -> 1
        }
    }

    private fun checkOpenThreeReverse(point: Coordinate, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(point, -dx, -dy)
        val (stone2, blink2) = search(point, dx, dy)

        val leftUp = stone1 + blink1
        val rightBottom = stone2 + blink2

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0

            dx != 0 && point.x.minus(leftUp) == 0 || point.x.minus(leftUp) == 14 -> 0
            dy != 0 && point.y.minus(leftUp) == 0 || point.y.minus(leftUp) == 14 -> 0
            dx != 0 && point.x.plus(rightBottom) == 0 || point.x.plus(rightBottom) == 14 -> 0
            dx != 0 && point.y.plus(rightBottom) == 0 || point.y.plus(rightBottom) == 14 -> 0

            board.board[point.y - rightBottom - 1][point.x + rightBottom + 1]?.color != goStone.color -> 0
            board.board[point.y + leftUp + 1][point.x - leftUp - 1]?.color != goStone.color -> 0
            else -> 1
        }
    }

    private fun checkOpenFour(point: Coordinate, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(point, -dx, -dy)
        val (stone2, blink2) = search(point, dx, dy)

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        when {
            blink1 + blink2 == 2 && stone1 + stone2 == 4 -> return 2
            blink1 + blink2 == 0 && stone1 + stone2 >= 5 -> return 2
            blink1 + blink2 == 2 && stone1 + stone2 == 5 -> return 2
            stone1 + stone2 != 3 -> return 0
            blink1 + blink2 == 2 -> return 0
        }

        val leftDownValid = when {
            dx != 0 && point.x.minus(dx * leftDown) == 0 || point.x.minus(dx * leftDown) == 14 -> 0
            dy != 0 && point.y.minus(dy * leftDown) == 0 || point.y.minus(dy * leftDown) == 14 -> 0
            board.board[point.y - dy * (leftDown + 1)][point.x - dx * (leftDown + 1)]?.color != goStone.color -> 0
            else -> 1
        }
        val rightUpValid = when {
            dx != 0 && point.x.plus(dx * rightUp) == 0 || point.x.plus(dx * rightUp) == 14 -> 0
            dy != 0 && point.y.plus(dy * rightUp) == 0 || point.y.plus(dy * rightUp) == 14 -> 0
            board.board[point.y + dy * (rightUp + 1)][point.x + dx * (rightUp + 1)]?.color != goStone.color -> 0
            else -> 1
        }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }

    private fun checkOpenFourReverse(point: Coordinate, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(point, -dx, -dy)
        val (stone2, blink2) = search(point, dx, dy)

        val leftUp = stone1 + blink1
        val rightBottom = stone2 + blink2

        when {
            blink1 + blink2 == 2 && stone1 + stone2 == 5 -> return 2
            blink1 + blink2 == 0 && stone1 + stone2 >= 5 -> return 2
            blink1 + blink2 == 2 && stone1 + stone2 == 4 -> return 2
            stone1 + stone2 != 3 -> return 0
            blink1 + blink2 == 2 -> return 0
        }

        val leftUpValid = when {
            dx != 0 && point.x.minus(leftUp) == 0 || point.x.minus(leftUp) == 14 -> 0
            dy != 0 && point.y.plus(leftUp) == 0 || point.y.plus(leftUp) == 14 -> 0
            board.board[point.y - rightBottom - 1][point.x + rightBottom + 1]?.color != goStone.color -> 0
            else -> 1
        }

        val rightBottomValid = when {
            dx != 0 && point.x.plus(rightBottom) == 0 || point.x.plus(rightBottom) == 14 -> 0
            dy != 0 && point.y.minus(rightBottom) == 0 || point.y.minus(rightBottom) == 14 -> 0
            board.board[point.y + leftUp + 1][point.x - leftUp - 1]?.color != goStone.color -> 0
            else -> 1
        }

        return if (leftUpValid + rightBottomValid >= 1) 1 else 0
    }

    private fun checkWin(point: Coordinate, dx: Int, dy: Int): Boolean {
        val (stone1, blink1) = search(point, -dx, -dy)
        val (stone2, blink2) = search(point, dx, dy)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 >= 4 -> true
            else -> false
        }
    }

    private fun search(point: Coordinate, dx: Int, dy: Int): Pair<Int, Int> {
        // var (toRight, toTop) = point
        var toRight = point.x
        var toTop = point.y
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (dx > 0 && toRight == 15) break
            if (dx < 0 && toRight == 1) break
            if (dy > 0 && toTop == 1) break
            if (dy < 0 && toTop == 15) break
            toRight += dx
            toTop += dy
            when {
                board.board[toTop][toRight] == this.goStone -> {
                    stone++
                    blink = blinkCount
                }

                board.board[toTop][toRight]?.color != this.goStone.color -> break
                board.board[toTop][toRight] == null -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }

                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }
}
