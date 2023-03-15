package omok.domain

class OmokRule(private val board: OmokBoard) {
    fun countOpenThrees(point: OmokPoint): Int =
        checkOpenThree(point, 1, 0) +
            checkOpenThree(point, 1, 1) +
            checkOpenThree(point, 0, 1) +
            checkOpenThreeReverse(point, 1, -1)

    private fun checkOpenThree(point: OmokPoint, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(point, -dx, -dy)
        val (stone2, blink2) = search(point, dx, dy)

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            point.x.minus(leftDown).isInEdge -> 0
            point.y.minus(leftDown).isInEdge -> 0
            point.x.plus(rightUp).isInEdge -> 0
            point.y.plus(rightUp).isInEdge -> 0
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
            point.x.minus(leftUp).isInEdge -> 0
            point.y.plus(leftUp).isInEdge -> 0
            point.x.plus(rightBottom).isInEdge -> 0
            point.y.minus(rightBottom).isInEdge -> 0
            board[point.y - rightBottom - 1][point.x + rightBottom + 1] == board.stoneState.next() -> 0
            board[point.y + leftUp + 1][point.x - leftUp - 1] == board.stoneState.next() -> 0
            else -> 1
        }
    }

    private fun search(point: OmokPoint, dx: Int, dy: Int): Pair<Int, Int> {
        var (toRight, toTop) = point
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (toTop.isInEdge || toRight.isInEdge) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                board.stoneState -> {
                    stone++
                    if (blink == 1) break
                    blink += blinkCount
                }
                board.stoneState.next() -> break
                StoneState.EMPTY -> if (blinkCount++ == 1) break
                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }
}
