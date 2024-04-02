package omok.model.rule

import omok.model.board.Board
import omok.model.position.Direction
import omok.model.position.Position
import omok.model.result.PutResult
import omok.model.stone.StoneType

abstract class RenjuRule(private val board: Array<Array<StoneType>>) {
    abstract fun check(position: Position): PutResult

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
            toRight += direction.row.value
            toTop += direction.column.value
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
        val rowDirection = direction.row.value
        val columnDirection = direction.column.value
        if (rowDirection > 0 && toRight == Board.MAX_AXIS) return true
        if (rowDirection < 0 && toRight == Board.MIN_AXIS) return true
        if (columnDirection > 0 && toTop == Board.MAX_AXIS) return true
        if (columnDirection < 0 && toTop == Board.MIN_AXIS) return true
        return false
    }

    companion object {
        val EMPTY_STONE = StoneType.NONE
        val CURRENT_STONE = StoneType.BLACK_STONE
        val OTHER_STONE = StoneType.WHITE_STONE
    }
}
