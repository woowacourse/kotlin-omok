package omok.domain

data class XCoordinate(val value: Int) {
    constructor(value: Char) : this(value - 'A' + 1)

    fun toChar(): Char = ('A' + value - 1)

    operator fun plus(other: Int) = XCoordinate(value + other)

    operator fun minus(other: Int) = XCoordinate(value - other)

    companion object {
        private const val X_MIN_RANGE = 1
        fun createXLine(size: Int): List<XCoordinate> = (X_MIN_RANGE..size).map(::XCoordinate)
    }
}
