package omok.domain

data class Position(
    val row: Int,
    val col: Int,
) {
    operator fun minus(value: Int) = Position(row - value, col - value)
}
