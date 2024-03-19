package omok.model.turn

import omok.model.entity.Point

interface Turn {
    fun placeStone(point: Point): Turn
}
