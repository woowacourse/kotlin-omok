package woowacourse.omok.domain.position

class Position(
    val row: Row,
    val col: Col,
) {
    fun move(
        rowStep: Int,
        colStep: Int,
    ): Position = Position(row + rowStep, col + colStep)

    fun isSame(position: Position): Boolean = this.col.isSame(position.col) && this.row.isSame(position.row)

    companion object {
        private const val ROW_LOWER_BOUND = 1
        private const val COL_LOWER_BOUND = 1
        private const val ROW_UPPER_BOUND = 15
        private const val COL_UPPER_BOUND = 15

        fun isMovable(
            position: Position,
            rowStep: Int,
            colStep: Int,
        ): Boolean = isRowInRange(position.row, rowStep) && isColInRange(position.col, colStep)

        private fun isRowInRange(
            row: Row,
            rowStep: Int,
        ) = (row.value + rowStep) in ROW_LOWER_BOUND..ROW_UPPER_BOUND

        private fun isColInRange(
            col: Col,
            cowStep: Int,
        ) = (col.value + cowStep) in COL_LOWER_BOUND..COL_UPPER_BOUND
    }
}
