package omok.library

import omok.model.PositionType

class WhiteStoneOmokRule : OmokRule {
    private lateinit var board: Array<Array<PositionType>>

    private val directions =
        listOf(
            Direction(1, 0),
            Direction(1, 1),
            Direction(0, 1),
            Direction(1, -1),
        )

    override fun isOmok(
        x: Int,
        y: Int,
        board: Array<Array<PositionType>>,
    ): Boolean {
        this.board = board
        return directions.any { checkOmok(y, x, it.dx, it.dy) }
    }

    private fun checkOmok(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Boolean {
        val (stone1, blink1) = search(y, x, -dx, -dy)
        val (stone2, blink2) = search(y, x, dx, dy)

        return blink1 + blink2 == 0 && stone1 + stone2 == 4
    }

    private fun search(
        y: Int,
        x: Int,
        dx: Int,
        dy: Int,
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
                PositionType.EMPTY, PositionType.BLOCK -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }

                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }

    data class Direction(val dx: Int, val dy: Int)

    companion object {
        private val currentStone: PositionType = PositionType.WHITE_STONE
        private val otherStone: PositionType = PositionType.BLACK_STONE
        private const val BOARD_SIZE: Int = 15
        private const val MIN_X = 0
    }
}
