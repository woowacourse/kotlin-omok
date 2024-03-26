package omok.model.state

import omok.model.Position

abstract class TurnState {
    abstract fun addStone(
        position: Position,
        placeStone: (Position) -> Unit,
    )
}
