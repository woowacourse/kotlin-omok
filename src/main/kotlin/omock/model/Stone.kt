package omock.model

data class Stone(
    val row: Row,
    val column: Column,
) {
    companion object {
        val stones = Row.ROW_RANGE.flatMap { rowComma ->
            (Column.COLUM_RANGE).map { columnComma ->
                Stone(
                    row = Row(rowComma),
                    column = Column(columnComma),
                )
            }
        }

        fun from(row: Row, column: Column): Stone {
            return stones.find {
                it.row == row && it.column == column
            } ?: throw IllegalArgumentException()
        }
    }
}
