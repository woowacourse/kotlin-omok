package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.Stone

interface PlayerState {
    val stone: Stone

    fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
    ): GameState
}
