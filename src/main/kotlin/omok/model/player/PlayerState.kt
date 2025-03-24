package omok.model.player

import omok.model.board.Position
import omok.model.omokGame.OmokGame

interface PlayerState {
    val omokGame: OmokGame

    fun state(position: Position): PlayerState
}
