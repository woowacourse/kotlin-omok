package model

class Position(
    val row: Row,
    val col: Col,
) {
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
        if (!other.row.isMax() && !other.col.isMin() && isUpDecreasingDiagonal(other)) {
            return true
        }
        if (!other.row.isMin() && !other.col.isMax() && isDownDecreasingDiagonal(other)) {
            return true
        }
        return false
    }

    fun isEdgePosition(direction: Direction): Boolean =
        when {
            direction.isGoing(Direction.LEFT) && this.col.isMin() -> true
            direction.isGoing(Direction.RIGHT) && this.col.isMax() -> true
            direction.isGoing(Direction.UP) && this.row.isMax() -> true
            direction.isGoing(Direction.DOWN) && this.row.isMin() -> true
            else -> false
        }

    private fun isMaxPosition(): Boolean = (this.col.isMax() || this.row.isMax())

    private fun isMinPosition(): Boolean = (this.col.isMin() || this.row.isMin())

    private fun isUpIncreasingDiagonal(other: Position): Boolean = row == (other.row + 1) && col == (other.col + 1)

    private fun isDownIncreasingDiagonal(other: Position): Boolean = row == (other.row - 1) && col == (other.col - 1)

    private fun isUpDecreasingDiagonal(other: Position): Boolean = row == (other.row + 1) && col == (other.col - 1)

    private fun isDownDecreasingDiagonal(other: Position): Boolean = row == (other.row - 1) && col == (other.col + 1)

    override fun equals(other: Any?): Boolean {
        other as Position
        if (row != other.row) return false
        if (col != other.col) return false
        return true
    }

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

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return col.toString()+row.value.toString()
    }
}
