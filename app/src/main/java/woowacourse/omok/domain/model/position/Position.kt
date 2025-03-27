package woowacourse.omok.domain.model.position

data class Position(val column: Column, val row: Row) {
    constructor(column: Int, row: Int, size: Int) : this(
        Column.from(column, size),
        Row.from(row, size),
    )
}
