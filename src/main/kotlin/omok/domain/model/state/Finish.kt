package omok.domain.model.state

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.stone.StoneType

class Finish(
    override val board: Board,
    override val stoneType: StoneType,
) : OmokState {
    override fun placeStone(position: Position): OmokState = this
}
