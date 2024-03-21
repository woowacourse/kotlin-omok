package omok.model.rule

import omok.model.board.Board
import omok.model.stone.Stone

open class OmokRule(private val board: Array<Array<Stone>>) {
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
        var toRight = x
        var toTop = y
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (isBoardRange(dx, toRight, dy, toTop)) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                CURRENT_STONE -> {
                    stone++
                    blink = blinkCount
                }

                OTHER_STONE -> break
                EMPTY_STONE -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }

                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }

    fun isBoardRange(
        dx: Int,
        toRight: Int,
        dy: Int,
        toTop: Int,
    ): Boolean {
        if (dx > 0 && toRight == Board.BOARD_SIZE - 1) return true
        if (dx < 0 && toRight == MIN_X) return true
        if (dy > 0 && toTop == Board.BOARD_SIZE - 1) return true
        if (dy < 0 && toTop == MIN_X) return true
        return false
    }

    companion object {
        val EMPTY_STONE = Stone.NONE
        const val MIN_X = 0
        const val MIN_Y = 0
        val CURRENT_STONE = Stone.BLACK_STONE
        val OTHER_STONE: Stone = Stone.WHITE_STONE
    }
}
