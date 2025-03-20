package omok.domain.model.state

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.stone.StoneType

sealed class OmokState(val board: Board) {
    abstract val stoneType: StoneType

    abstract fun placeStone(onPlace: () -> Position): OmokState
}
