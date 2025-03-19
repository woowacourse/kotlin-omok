package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState
import omok.model.stone.StoneColor

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
