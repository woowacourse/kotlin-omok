package domain.stone

import domain.X_MAX_RANGE
import domain.X_MIN_RANGE

class XCoordinate private constructor(val x: Char) {

    companion object {
        private val COORDINATES: MutableMap<Char, XCoordinate> =
            (X_MIN_RANGE..X_MAX_RANGE).associateWith { XCoordinate(it) }.toMutableMap()

        fun of(x: Char): XCoordinate {
            val upperX = x.uppercase()[0]
            return COORDINATES[upperX] ?: run {
                COORDINATES[x] = XCoordinate(x)
                return XCoordinate(x)
            }
        }
    }
}
