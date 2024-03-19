package omok.model.turn

import omok.model.entity.Point
import omok.model.entity.StoneColor

interface Turn {
    fun placeStone(point: Point): Turn

    fun color(): StoneColor
}
