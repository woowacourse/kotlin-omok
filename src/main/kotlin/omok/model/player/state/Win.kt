package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.PositionState

class Win : PlayerState {
    override fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
        positionState: PositionState,
    ): PlayerState = this
}
