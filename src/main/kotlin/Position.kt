class Position(
    val row: Row,
    val col: Col,
) {
    fun isSameRow(other: Position): Boolean {
        return row.isSame(other.row)
    }
}
