package omok.model.rule

import omok.model.board.Board
import omok.model.position.Direction
import omok.model.position.Position
import omok.model.stone.StoneType

open class RenjuRule(private val board: Array<Array<StoneType>>) {
    fun search(
        position: Position,
        direction: Direction,
    ): Pair<Int, Int> {
        var toRight = position.row.value
        var toTop = position.column.value
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (isBoardRange(direction, toRight, toTop)) break
            toRight += direction.rowDirection
            toTop += direction.columnDirection
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
        direction: Direction,
        toRight: Int,
        toTop: Int,
    ): Boolean {
        val deltaRow = direction.rowDirection
        val deltaCol = direction.columnDirection
        if (deltaRow > 0 && toRight == Board.MAX_AXIS) return true
        if (deltaRow < 0 && toRight == Board.MIN_AXIS) return true
        if (deltaCol > 0 && toTop == Board.MAX_AXIS) return true
        if (deltaCol < 0 && toTop == Board.MIN_AXIS) return true
        return false
    }

    companion object {
        val EMPTY_STONE = StoneType.NONE
        val CURRENT_STONE = StoneType.BLACK_STONE
        val OTHER_STONE = StoneType.WHITE_STONE
    }
}
