package omok.model.state

import omok.model.Position

class White : TurnState() {
    override fun addStone(
        position: Position,
        placeStone: (Position) -> Unit,
    ) {
        placeStone(position)
    }
}
