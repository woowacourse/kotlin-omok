package omok.domain.state

import omok.domain.Board
import omok.domain.StoneColor
import omok.domain.Stones
import rule.wrapper.point.Point

interface State {
    val whiteStones: Stones
    val blackStones: Stones

    fun place(
        point: Point,
        boardSize: Int = Board.DEFAULT_BOARD_SIZE,
    ): State

    fun lastStonePoint(): Point?

    fun nextStoneColor(): StoneColor
}
