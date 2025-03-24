package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position

interface PlayerState {
    fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
    ): GameState
}
