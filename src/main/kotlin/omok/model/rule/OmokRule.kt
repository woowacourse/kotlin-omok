package omok.model.rule

import omok.model.board.Board
import omok.model.stone.StoneType

open class OmokRule(private val board: Array<Array<StoneType>>) {
    val directions = listOf(listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(1, -1))

    fun checkRenjuRule(
        row: Int,
        col: Int,
    ): Boolean =
        ThreeThreeChecker.checkThreeThree(row, col) ||
            FourFourChecker.checkFourFour(row, col) ||
            ExceedFiveChecker.checkMoreThanFive(row, col)

    fun search(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Pair<Int, Int> {
        // 반대의 반대의 반대,,
        var toRight = x
        var toTop = y
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (dx > 0 && toRight == Board.BOARD_SIZE - 1) break
            if (dx < 0 && toRight == MIN_X) break
            if (dy > 0 && toTop == Board.BOARD_SIZE - 1) break
            if (dy < 0 && toTop == MIN_X) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                CURRENT_STONEType -> {
                    stone++
                    blink = blinkCount
                }

                OTHER_STONEType -> break
                EMPTY_STONEType -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }

                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }

    companion object {
        val EMPTY_STONEType = StoneType.NONE
        const val MIN_X = 0
        const val MIN_Y = 0
        val CURRENT_STONEType = StoneType.BLACK_STONE
        val OTHER_STONEType: StoneType = StoneType.WHITE_STONE
    }
}
