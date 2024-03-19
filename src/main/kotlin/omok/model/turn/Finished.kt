package omok.model.turn

import omok.model.entity.Point
import omok.model.entity.StoneColor

class Finished(val color: StoneColor) : Turn {
    override fun placeStone(point: Point): Turn {
        throw IllegalStateException()
    }

    override fun color(): StoneColor {
        return color
    }
}
