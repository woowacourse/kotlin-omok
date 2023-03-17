package omok.domain

import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState

class OmokRule(private val board: OmokBoard, private val stoneState: StoneState) {
    fun countOpenThrees(point: OmokPoint): Int =
        checkOpenThree(point, 1, 0) +
            checkOpenThree(point, 1, 1) +
            checkOpenThree(point, 0, 1) +
            checkOpenThree(point, 1, -1)

    fun countOpenFours(point: OmokPoint): Int =
        checkOpenFour(point, 1, 0) +
            checkOpenFour(point, 1, 1) +
            checkOpenFour(point, 0, 1) +
            checkOpenFour(point, 1, -1)

    fun validateWhiteWin(point: OmokPoint): Boolean =
        checkWhiteWin(point, 1, 0) ||
            checkWhiteWin(point, 1, 1) ||
            checkWhiteWin(point, 0, 1) ||
            checkWhiteWin(point, -1, 1)

    fun validateBlackWin(point: OmokPoint): Boolean =
        checkBlackWin(point, 1, 0) ||
            checkBlackWin(point, 1, 1) ||
            checkBlackWin(point, 0, 1) ||
            checkBlackWin(point, -1, 1)

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
            board[OmokPoint(point.x - dx * (leftDown + 1), point.y - dy * (leftDown + 1))] == stoneState.next() -> 0
            board[OmokPoint(point.x + dx * (rightUp + 1), point.y + dy * (rightUp + 1))] == stoneState.next() -> 0
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
            blink1 + blink2 == 2 && stone1 + stone2 == 5 -> return 2
            stone1 + stone2 != 3 -> return 0
            blink1 + blink2 == 2 -> return 0
        }

        val leftDownValid = when {
            dx != 0 && point.x.minus(dx * leftDown).isInEdge -> 0
            dy != 0 && point.y.minus(dy * leftDown).isInEdge -> 0
            board[OmokPoint(point.x - dx * (leftDown + 1), point.y - dy * (leftDown + 1))] == stoneState.next() -> 0
            else -> 1
        }
        val rightUpValid = when {
            dx != 0 && point.x.plus(dx * rightUp).isInEdge -> 0
            dy != 0 && point.y.plus(dy * rightUp).isInEdge -> 0
            board[OmokPoint(point.x + dx * (rightUp + 1), point.y + dy * (rightUp + 1))] == stoneState.next() -> 0
            else -> 1
        }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }

    private fun checkBlackWin(point: OmokPoint, dx: Int, dy: Int): Boolean {
        val (stone1, blink1) = search(point, -dx, -dy)
        val (stone2, blink2) = search(point, dx, dy)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 == 4 -> true
            else -> false
        }
    }

    private fun checkWhiteWin(point: OmokPoint, dx: Int, dy: Int): Boolean {
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
            when (board[OmokPoint(toRight, toTop)]) {
                stoneState -> {
                    stone++
                    blink = blinkCount
                }
                stoneState.next() -> break
                EmptyStoneState -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }
                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }

    companion object {
        const val MIN_OPEN_THREES = 2
        const val MIN_OPEN_FOURS = 2
    }
}
