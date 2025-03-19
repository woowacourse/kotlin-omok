package omok.rule

import omok.board.Board
import omok.board.Point
import omok.board.PointState
import omok.stone.StoneColor

interface Rule {
    fun calculate(
        board: Board,
        previousPoint: Point,
    ): Boolean

    companion object {
        fun PointState.toStoneColor(): StoneColor? =
            when (this) {
                PointState.BLACK -> StoneColor.BLACK
                PointState.WHITE -> StoneColor.WHITE
                else -> null
            }
    }
}
