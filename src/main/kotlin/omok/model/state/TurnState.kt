package omok.model.state

import omok.model.Color
import omok.model.Position

abstract class TurnState {
    abstract fun addStone(
        position: Position,
        placeStone: (Color, Position) -> Unit,
    )
}
