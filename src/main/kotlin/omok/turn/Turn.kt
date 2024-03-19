package omok.turn

import omok.Point

interface Turn {
    fun placeStone(point: Point): Turn
}
