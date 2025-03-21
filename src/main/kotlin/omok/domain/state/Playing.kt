package omok.domain.state

import omok.domain.Board
import omok.domain.stone.StoneColor
import rule.wrapper.point.Point

interface Playing : State {
    fun place(
        point: Point,
        boardSize: Int = Board.DEFAULT_BOARD_SIZE,
    ): State

    fun lastStonePoint(): Point?

    fun nextStoneColor(): StoneColor
}