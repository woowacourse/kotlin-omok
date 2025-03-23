package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.PositionState

interface PlayerState {
    fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
        positionState: PositionState,
    ): PlayerState
}
