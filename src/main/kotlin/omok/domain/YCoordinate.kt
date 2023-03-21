package omok.domain

data class YCoordinate(val value: Int) {
    operator fun plus(other: Int) = YCoordinate(value + other)

    operator fun minus(other: Int) = YCoordinate(value - other)

    companion object {
        private const val Y_MIN_RANGE = 1
        fun createYLine(size: Int): List<YCoordinate> = (Y_MIN_RANGE..size).map(::YCoordinate)
    }
}
