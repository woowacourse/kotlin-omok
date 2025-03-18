class Position(
    val row: Row,
    val col: Col,
) {
    fun isSameRow(other: Position): Boolean = row.isSame(other.row)

    fun isSameCol(other: Position): Boolean = col.isSame(other.col)

    fun isIncreasingDiagonal(other: Position): Boolean {
        if (!(isMaxPosition(other)) && isUpIncreasingDiagonal(other)) {
            return true
        }
        if (!(isMinPosition(other)) && isDownIncreasingDiagonal(other)) {
            return true
        }
        return false
    }

    fun isDecreasingDiagonal(other: Position): Boolean {
        if (!(isMaxPosition(other)) && isUpDecreasingDiagonal(other)) {
            return true
        }
        if (!(isMinPosition(other)) && isDownDecreasingDiagonal(other)) {
            return true
        }
        return false
    }

    private fun isMaxPosition(other: Position): Boolean = (other.col.isMax() || other.row.isMax())

    private fun isMinPosition(other: Position): Boolean = (other.col.isMin() || other.row.isMin())

    private fun isUpIncreasingDiagonal(other: Position): Boolean = row.isSame(other.row + 1) && col.isSame(other.col + 1)

    private fun isDownIncreasingDiagonal(other: Position): Boolean = row.isSame(other.row - 1) && col.isSame(other.col - 1)

    private fun isUpDecreasingDiagonal(other: Position): Boolean = row.isSame(other.row + 1) && col.isSame(other.col - 1)

    private fun isDownDecreasingDiagonal(other: Position): Boolean = row.isSame(other.row - 1) && col.isSame(other.col + 1)
}
