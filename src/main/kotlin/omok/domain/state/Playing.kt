package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.Point
import omok.domain.stone.StoneColor

interface Playing : State {
    fun place(
        point: Point,
        boardSize: Int = OmokBoard.DEFAULT_BOARD_SIZE,
    ): State

    fun lastStonePoint(): Point?

    fun nextStoneColor(): StoneColor
}
