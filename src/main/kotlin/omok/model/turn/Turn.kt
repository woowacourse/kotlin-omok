package omok.model.turn

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor

abstract class Turn(
    val board: Board,
) {
    abstract fun placeStone(point: Point, printError: (String) -> Unit): Turn

    abstract fun color(): StoneColor
}
