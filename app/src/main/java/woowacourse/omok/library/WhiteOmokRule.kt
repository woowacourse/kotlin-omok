package omok.library

import woowacourse.omok.CoordinateState

object WhiteOmokRule : OmokRule {
    private val currentStone: CoordinateState = CoordinateState.WhiteStone
    private val otherStone: CoordinateState = CoordinateState.BlackStone
    private const val BOARD_SIZE: Int = 15
    private const val MIN_X = 0

    private val directions =
        listOf(
            Pair(1, 0),
            Pair(1, 1),
            Pair(0, 1),
            Pair(1, -1),
        )

    override fun isOmok(
        x: Int,
        y: Int,
        board: Array<Array<CoordinateState>>,
    ): Boolean {
        return directions.any { checkOmok(y, x, it.first, it.second, board) }
    }

    override fun isWin(
        x: Int,
        y: Int,
        board: Array<Array<CoordinateState>>,
    ): Boolean = isOmok(x, y, board)

    private fun checkOmok(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
        board: Array<Array<CoordinateState>>,
    ): Boolean {
        val (stone1, blink1) = search(y, x, -dx, -dy, board)
        val (stone2, blink2) = search(y, x, dx, dy, board)

        return blink1 + blink2 == 0 && stone1 + stone2 == 4
    }

    private fun search(
        y: Int,
        x: Int,
        dx: Int,
        dy: Int,
        board: Array<Array<CoordinateState>>,
    ): Pair<Int, Int> {
        var toRight = x
        var toTop = y
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (dx > 0 && toRight == BOARD_SIZE - 1) break
            if (dx < 0 && toRight == MIN_X) break
            if (dy > 0 && toTop == BOARD_SIZE - 1) break
            if (dy < 0 && toTop == MIN_X) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                currentStone -> {
                    stone++
                    blink = blinkCount
                }

                otherStone -> break
                CoordinateState.Empty, CoordinateState.Forbidden -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }

                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }
}
