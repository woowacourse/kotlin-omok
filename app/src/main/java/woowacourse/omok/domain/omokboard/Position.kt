package woowacourse.omok.domain.omokboard

data class Position(
    val row: RowPosition,
    val column: ColumnPosition,
) {
    constructor(columnRow: Pair<Int, Int>) : this(RowPosition(columnRow.first), ColumnPosition(columnRow.second))
}
