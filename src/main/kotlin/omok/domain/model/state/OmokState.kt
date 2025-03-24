package omok.domain.model.state

import omok.domain.model.position.Position
import omok.domain.model.stone.StoneType
import omok.domain.model.stone.Stones

interface OmokState {
    val stoneType: StoneType
    val stones: Stones

    fun placeStone(position: Position): OmokState
}
