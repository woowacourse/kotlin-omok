package omok.model.rule

import omok.model.board.Board
import omok.model.position.DeltaPosition
import omok.model.position.Position
import omok.model.stone.Stone

open class RenjuRule(private val board: Array<Array<Stone>>) {
    val directions = listOf(listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(1, -1))

    fun checkRenjuRule(position: Position): Boolean =
        DoubleThreeChecker.isDoubleThree(position) ||
            DoubleFourChecker.isDoubleFour(position) ||
            ExceedFiveChecker.isMoreThanFive(position)

    fun search(
        position: Position,
        deltaPosition: DeltaPosition,
    ): Pair<Int, Int> {
        var toRight = position.row
        var toTop = position.col
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (isBoardRange(deltaPosition, toRight, toTop)) break
            toRight += deltaPosition.deltaRow
            toTop += deltaPosition.deltaCol
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
        deltaPosition: DeltaPosition,
        toRight: Int,
        toTop: Int,
    ): Boolean {
        val deltaRow = deltaPosition.deltaRow
        val deltaCol = deltaPosition.deltaCol
        if (deltaRow > 0 && toRight == Board.BOARD_SIZE - 1) return true
        if (deltaRow < 0 && toRight == MIN_X) return true
        if (deltaCol > 0 && toTop == Board.BOARD_SIZE - 1) return true
        if (deltaCol < 0 && toTop == MIN_X) return true
        return false
    }

    companion object {
        const val MIN_X = 0
        const val MIN_Y = 0
        val EMPTY_STONE = Stone.NONE
        val CURRENT_STONE = Stone.BLACK_STONE
        val OTHER_STONE: Stone = Stone.WHITE_STONE
    }
}
