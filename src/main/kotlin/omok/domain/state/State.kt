package omok.domain.state

import omok.domain.Board
import omok.domain.stone.BlackStones
import omok.domain.stone.StoneColor
import omok.domain.stone.WhiteStones
import rule.wrapper.point.Point

interface State {
    val blackStones: BlackStones
    val whiteStones: WhiteStones

    fun place(
        point: Point,
        boardSize: Int = Board.DEFAULT_BOARD_SIZE,
    ): State

    fun lastStonePoint(): Point?

    fun nextStoneColor(): StoneColor
}
