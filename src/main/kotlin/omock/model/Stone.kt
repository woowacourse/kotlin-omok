package omock.model

data class Stone(
    val row: Row, val column: Column
) {
    companion object {
        private val stones = Column.COLUM_RANGE.zip(Row.COLUM_RANGE).map { (columnComma, rowComma) ->
            Stone(
                row = Row(rowComma),
                column = Column(columnComma),
            )
        }

        fun from(row: Row, column: Column): Stone {
            return stones.find {
                it.row == row && it.column == column
            } ?: throw IllegalArgumentException()
        }
    }
}
