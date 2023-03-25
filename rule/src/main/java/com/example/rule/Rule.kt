package com.example.rule

import com.example.rule.state.EmptyStoneState

class Rule(private val board: ArkBoard) {
    fun countOpenThrees(point: OmokPoint): Int =
        checkOpenThree(point, 1, 0) +
            checkOpenThree(point, 1, 1) +
            checkOpenThree(point, 0, 1) +
            checkOpenThreeReverse(point, 1, -1)

    fun countOpenFours(point: OmokPoint): Int =
        checkOpenFour(point, 1, 0) +
            checkOpenFour(point, 1, 1) +
            checkOpenFour(point, 0, 1) +
            checkOpenFourReverse(point, 1, -1)

    fun validateWin(point: OmokPoint): Boolean =
        checkWin(point, 1, 0) ||
            checkWin(point, 1, 1) ||
            checkWin(point, 0, 1) ||
            checkWin(point, -1, 1)

    private fun checkOpenThree(point: OmokPoint, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(point, -dx, -dy)
        val (stone2, blink2) = search(point, dx, dy)

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && point.x.minus(leftDown).isInEdge -> 0
            dy != 0 && point.y.minus(leftDown).isInEdge -> 0
            dx != 0 && point.x.plus(rightUp).isInEdge -> 0
            dy != 0 && point.y.plus(rightUp).isInEdge -> 0
            board[point.y - dy * (leftDown + 1)][point.x - dx * (leftDown + 1)] == board.stoneState.next() -> 0
            board[point.y + dy * (rightUp + 1)][point.x + dx * (rightUp + 1)] == board.stoneState.next() -> 0
            else -> 1
        }
    }

    private fun checkOpenThreeReverse(point: OmokPoint, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(point, -dx, -dy)
        val (stone2, blink2) = search(point, dx, dy)

        val leftUp = stone1 + blink1
        val rightBottom = stone2 + blink2

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && point.x.minus(leftUp).isInEdge -> 0
            dy != 0 && point.y.plus(leftUp).isInEdge -> 0
            dx != 0 && point.x.plus(rightBottom).isInEdge -> 0
            dy != 0 && point.y.minus(rightBottom).isInEdge -> 0
            board[point.y - rightBottom - 1][point.x + rightBottom + 1] == board.stoneState.next() -> 0
            board[point.y + leftUp + 1][point.x - leftUp - 1] == board.stoneState.next() -> 0
            else -> 1
        }
    }

    private fun checkOpenFour(point: OmokPoint, dx: Int, dy: Int): Int {
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
            dx != 0 && point.x.minus(dx * leftDown).isInEdge -> 0
            dy != 0 && point.y.minus(dy * leftDown).isInEdge -> 0
            board[point.y - dy * (leftDown + 1)][point.x - dx * (leftDown + 1)] == board.stoneState.next() -> 0
            else -> 1
        }
        val rightUpValid = when {
            dx != 0 && point.x.plus(dx * rightUp).isInEdge -> 0
            dy != 0 && point.y.plus(dy * rightUp).isInEdge -> 0
            board[point.y + dy * (rightUp + 1)][point.x + dx * (rightUp + 1)] == board.stoneState.next() -> 0
            else -> 1
        }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }

    private fun checkOpenFourReverse(point: OmokPoint, dx: Int, dy: Int): Int {
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
            dx != 0 && point.x.minus(leftUp).isInEdge -> 0
            dy != 0 && point.y.plus(leftUp).isInEdge -> 0
            board[point.y - rightBottom - 1][point.x + rightBottom + 1] == board.stoneState.next() -> 0
            else -> 1
        }

        val rightBottomValid = when {
            dx != 0 && point.x.plus(rightBottom).isInEdge -> 0
            dy != 0 && point.y.minus(rightBottom).isInEdge -> 0
            board[point.y + leftUp + 1][point.x - leftUp - 1] == board.stoneState.next() -> 0
            else -> 1
        }

        return if (leftUpValid + rightBottomValid >= 1) 1 else 0
    }

    private fun checkWin(point: OmokPoint, dx: Int, dy: Int): Boolean {
        val (stone1, blink1) = search(point, -dx, -dy)
        val (stone2, blink2) = search(point, dx, dy)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 >= 4 -> true
            else -> false
        }
    }

    private fun search(point: OmokPoint, dx: Int, dy: Int): Pair<Int, Int> {
        var (toRight, toTop) = point
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (dx > 0 && toRight.isRightMost) break
            if (dx < 0 && toRight.isLeftMost) break
            if (dy > 0 && toTop.isTopMost) break
            if (dy < 0 && toTop.isBottomMost) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                board.stoneState -> {
                    stone++
                    blink = blinkCount
                }

                board.stoneState.next() -> break
                EmptyStoneState -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }

                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }
}
