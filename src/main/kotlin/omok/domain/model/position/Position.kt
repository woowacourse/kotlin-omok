package omok.domain.model.position

data class Position(val column: Column, val row: Row) {
    constructor(column: Int, inColumnRange: Boolean, row: Int, inRowRange: Boolean) : this(
        Column.from(column, inColumnRange),
        Row.from(row, inRowRange),
    )
}
