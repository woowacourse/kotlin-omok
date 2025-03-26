package woowacourse.omok.domain.omokboard

data class Position(
    val row: RowPosition,
    val column: ColumnPosition,
) {
    override fun toString(): String {
        return "${ColumnPosition.toLabel(this.column)}${this.row}"
    }

    constructor(row: Int, column: Char) : this(RowPosition(row), ColumnPosition.fromChar(column))
    constructor(columnRow: String) : this(RowPosition(columnRow.substring(1).toInt()), ColumnPosition.fromChar(columnRow.first()))
}
