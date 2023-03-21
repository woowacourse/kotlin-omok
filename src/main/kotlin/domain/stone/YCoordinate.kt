package domain.stone

import domain.Y_MAX_RANGE
import domain.Y_MIN_RANGE

class YCoordinate private constructor(val y: Int) {

    companion object {
        private val COORDINATES: MutableMap<Int, YCoordinate> = (Y_MIN_RANGE .. Y_MAX_RANGE).associateWith { YCoordinate(it) }.toMutableMap()

        fun of(y: Int): YCoordinate = COORDINATES[y] ?:run{
            COORDINATES[y] = YCoordinate(y)
            return YCoordinate(y)
        }
    }
}
