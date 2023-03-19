package domain

class XCoordinate private constructor(val x: Char) {

    companion object {
        const val X_MIN_RANGE = 'A'
        const val X_MAX_RANGE = 'O'
        private val COORDINATES: MutableMap<Char, XCoordinate> = (X_MIN_RANGE..X_MAX_RANGE).associateWith { XCoordinate(it) }.toMutableMap()

        fun of(x: Char): XCoordinate {
            val upperX = x.uppercase()[0]
            return COORDINATES[upperX] ?: run{
                COORDINATES[x] = XCoordinate(x)
                return XCoordinate(x)
            }
        }
    }
}
