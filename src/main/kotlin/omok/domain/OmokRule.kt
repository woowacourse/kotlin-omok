package omok.domain

class OmokRule(private val board: OmokBoard) {

    fun findSamSam(point: OmokPoint): Int {
        board.placeStone(point)
        var openSamCount = 0
        openSamCount += find(point, 1, 0)
        openSamCount += find(point, 1, 1)
        openSamCount += find(point, 0, 1)
        openSamCount += findReverse(point, 1, -1)
        return openSamCount
    }
    private fun find(point: OmokPoint, dx: Int, dy: Int): Int {
        val o = board.stoneState.next()
        val (stone1, blink1) = search(point, -dx, -dy)
        val (stone2, blink2) = search(point, dx, dy)

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        if (stone1 + stone2 != 2) return 0
        return when {
            point.x.minus(leftDown).isInEdge -> 0
            point.y.minus(leftDown).isInEdge -> 0
            point.x.plus(rightUp).isInEdge -> 0
            point.y.plus(rightUp).isInEdge -> 0
            board[point.y - dy * (leftDown + 1)][point.x - dx * (leftDown + 1)] == o -> 0
            board[point.y + dy * (rightUp + 1)][point.x + dx * (rightUp + 1)] == o -> 0
            else -> 1
        }
    }

    private fun findReverse(point: OmokPoint, dx: Int, dy: Int): Int {
        val o = board.stoneState.next()
        val (stone1, blink1) = search(point, -dx, -dy)
        val (stone2, blink2) = search(point, dx, dy)

        val leftUp = stone1 + blink1
        val rightBottom = stone2 + blink2

        if (stone1 + stone2 != 2) return 0

        return when {
            point.x.minus(leftUp).isInEdge -> 0
            point.y.plus(leftUp).isInEdge -> 0
            point.x.plus(rightBottom).isInEdge -> 0
            point.y.minus(rightBottom).isInEdge -> 0
            board[point.y - rightBottom - 1][point.x + rightBottom + 1] == o -> 0
            board[point.y + leftUp + 1][point.x - leftUp - 1] == o -> 0
            else -> 1
        }
    }

    private fun search(
        point: OmokPoint,
        dx: Int,
        dy: Int,
    ): Pair<Int, Int> {
        val p: StoneState = board.stoneState
        val o: StoneState = board.stoneState.next()
        var stone = 0
        var toRight = point.x
        var toTop = point.y
        var blink = 0
        var blinkTemp = 0
        while (true) {
            if (toTop.isInEdge || toRight.isInEdge) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                p -> {
                    stone++
                    if (blinkTemp == 1) {
                        if (blink == 1) break
                        blink++
                    }
                }

                o -> break
                StoneState.EMPTY -> {
                    if (++blinkTemp == 2) break
                }

                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }
}
