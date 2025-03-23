package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position

class Win : PlayerState {
    override fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
    ): PlayerState = this
}
