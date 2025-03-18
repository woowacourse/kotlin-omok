package omok

data class Position private constructor(val row: Line, val column: Line) {
    companion object {
        fun of(
            row: Int,
            column: Int,
        ): Position = Position(Line(row), Line(column))
    }
}
