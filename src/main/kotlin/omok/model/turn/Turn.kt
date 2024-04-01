package omok.model.turn

import omok.model.board.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor

interface Turn {
    val board: Board

    fun placeStone(
        point: Point,
        onInappropriate: (String) -> Unit,
    ): Turn

    fun color(): StoneColor
}
