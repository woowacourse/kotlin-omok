package omok.model.player

import omok.model.board.Position
import omok.model.omokGame.OmokGame
import omok.model.stone.StoneState

interface PlayerState {
    val omokGame: OmokGame

    fun stoneState(): StoneState

    fun state(position: Position): PlayerState
}
