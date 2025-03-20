package omok.domain.model.state

import omok.domain.model.Board
import omok.domain.model.position.Position

sealed class OmokState(val board: Board) {
    abstract fun placeStone(onPlace: () -> Position): OmokState
}
