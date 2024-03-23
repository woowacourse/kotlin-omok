package omok.model.state

import omok.model.Color
import omok.model.Position

class White(whiteStatus: Array<Array<Color>>) : TurnState(whiteStatus) {
    override fun addStone(
        position: Position,
        placeStone: (Color, Position) -> Unit,
    ) {
        placeStone(Color.WHITE, position)
    }
}
