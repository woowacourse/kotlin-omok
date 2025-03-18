package omok.board

import omok.stone.Position
import omok.stone.StoneColor

class Board(val points: Points = Points.create()) {
    fun placeStone(
        position: Position,
        color: StoneColor,
    ) {
        points.placeStone(position, color)
    }
}
