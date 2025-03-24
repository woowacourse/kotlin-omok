package omok.model.player

import omok.model.OmokGame
import omok.model.board.Position

interface PlayerState {
    val omokGame: OmokGame

    fun state(position: Position): PlayerState
}
