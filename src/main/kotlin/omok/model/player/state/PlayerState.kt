package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.StoneState

interface PlayerState {
    fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
        stoneState: StoneState,
    ): PlayerState
}
