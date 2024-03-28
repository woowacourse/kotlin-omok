package omok.model.turn

import PlaceStoneInterrupt
import omok.model.Board
import omok.model.Either
import omok.model.entity.Point
import omok.model.entity.StoneColor

abstract class Turn(
    val board: Either<PlaceStoneInterrupt, Board>,
) {
    abstract fun proceed(point: Point): Turn

    abstract fun color(): StoneColor
}
