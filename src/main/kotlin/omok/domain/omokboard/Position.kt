package omok.domain.omokboard

data class Position(
    val row: RowPosition,
    val column: ColumnPosition,
) {
    constructor(row: Int, column: Char) : this(RowPosition(row), ColumnPosition.fromChar(column))
}
