class Position(
    val row: Row,
    val col: Col,
) {
    fun isSameRow(other: Position): Boolean = row.isSame(other.row)

    fun isSameCol(other: Position): Boolean = col.isSame(other.col)
}
