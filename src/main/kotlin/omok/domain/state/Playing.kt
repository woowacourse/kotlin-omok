package omok.domain.state

import omok.domain.Board
import omok.domain.Point
import omok.domain.stone.StoneColor

interface Playing : State {
    fun place(
        point: Point,
        boardSize: Int = Board.DEFAULT_BOARD_SIZE,
    ): State

    fun nextStoneColor(): StoneColor
}
