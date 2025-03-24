package omok.domain.model.state

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.stone.StoneType

interface OmokState {
    val stoneType: StoneType
    val board: Board

    fun placeStone(position: Position): OmokState
}
