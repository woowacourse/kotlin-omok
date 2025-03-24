package omok.domain.model.state

import omok.domain.model.position.Position
import omok.domain.model.stone.StoneType
import omok.domain.model.stone.Stones

class Finish(
    override val stones: Stones,
    override val stoneType: StoneType,
) : OmokState {
    override fun placeStone(position: Position): OmokState = this
}
