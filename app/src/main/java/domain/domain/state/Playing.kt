package domain.domain.state

import domain.domain.Board
import domain.domain.Point
import domain.domain.stone.BlackStones
import domain.domain.stone.StoneColor
import domain.domain.stone.WhiteStones

interface Playing : State {
    val blackStones: BlackStones
    val whiteStones: WhiteStones

    fun place(
        point: Point,
        boardSize: Int = Board.DEFAULT_BOARD_SIZE,
        onBoardUpdated: (BlackStones, WhiteStones) -> Unit,
    ): State

    fun nextStoneColor(): StoneColor
}
