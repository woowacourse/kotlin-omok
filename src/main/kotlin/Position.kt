class Position(
    val row: Row,
    val col: Col,
) {
    fun isSamePosition(position: Position): Boolean = row.isSame(position.row) && col.isSame(position.col)

    fun isSameRow(other: Position): Boolean = row.isSame(other.row)

    fun isSameCol(other: Position): Boolean = col.isSame(other.col)

    fun isIncreasingDiagonal(other: Position): Boolean {
        if (!(other.isMaxPosition()) && isUpIncreasingDiagonal(other)) {
            return true
        }
        if (!(other.isMinPosition()) && isDownIncreasingDiagonal(other)) {
            return true
        }
        return false
    }

    fun isDecreasingDiagonal(other: Position): Boolean {
        if (!other.isMaxPosition() && isUpDecreasingDiagonal(other)) {
            return true
        }
        if (!other.isMinPosition() && isDownDecreasingDiagonal(other)) {
            return true
        }
        return false
    }

    fun isEdgePosition(direction: Direction): Boolean =
        when {
            direction.isGoLeft() && this.col.isMin() -> true
            direction.isGoRight() && this.col.isMax() -> true
            direction.isGoUp() && this.row.isMax() -> true
            direction.isGoDown() && this.row.isMin() -> true
            else -> false
        }

    private fun isMaxPosition(): Boolean = (this.col.isMax() || this.row.isMax())

    private fun isMinPosition(): Boolean = (this.col.isMin() || this.row.isMin())

    private fun isUpIncreasingDiagonal(other: Position): Boolean = row.isSame(other.row + 1) && col.isSame(other.col + 1)

    private fun isDownIncreasingDiagonal(other: Position): Boolean = row.isSame(other.row - 1) && col.isSame(other.col - 1)

    private fun isUpDecreasingDiagonal(other: Position): Boolean = row.isSame(other.row + 1) && col.isSame(other.col - 1)

    private fun isDownDecreasingDiagonal(other: Position): Boolean = row.isSame(other.row - 1) && col.isSame(other.col + 1)

    fun moveOrNull(
        direction: Direction,
        offset: Int,
    ): Position? {
        var position = Position(row, col)
        runCatching {
            position = Position(row + direction.rowStep * (offset), col + direction.colStep * (offset))
        }.onFailure { return null }
        return position
    }
}
