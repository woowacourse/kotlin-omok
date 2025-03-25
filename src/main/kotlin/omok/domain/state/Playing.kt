package omok.domain.state

import omok.domain.Board
import omok.domain.Point
import omok.domain.stone.BlackStones
import omok.domain.stone.StoneColor
import omok.domain.stone.WhiteStones

interface Playing : State {
    val blackStones: BlackStones
    val whiteStones: WhiteStones

    fun place(
        point: Point,
        boardSize: Int = Board.DEFAULT_BOARD_SIZE,
        onBoardUpdated: (Set<Point>, Set<Point>) -> Unit,
    ): State

    fun nextStoneColor(): StoneColor
}
