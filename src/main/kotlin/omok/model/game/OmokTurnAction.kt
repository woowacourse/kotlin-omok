package omok.model.game

import omok.model.board.Board
import omok.model.board.Position
import omok.model.board.Stone

interface OmokTurnAction {
    fun nextStonePosition(
        nowOrderStone: Stone,
        recentPosition: Position?,
    ): Position

    fun onStonePlace(board: Board)
}
