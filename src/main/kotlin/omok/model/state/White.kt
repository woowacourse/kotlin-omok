package omok.model.state

import omok.model.Color
import omok.model.Position

class White : TurnState() {
    override fun addStone(
        position: Position,
        placeStone: (Color, Position) -> Unit,
    ) {
        placeStone(Color.WHITE, position)
    }
}
