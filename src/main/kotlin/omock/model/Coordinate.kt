package omock.model

data class Coordinate(
    val row: Row, val column: Column
) {
    companion object {
        private val coordinates = Column.COLUM_RANGE.zip(Row.COLUM_RANGE).map { (columnComma, rowComma) ->
            Coordinate(
                row = Row(rowComma),
                column = Column(columnComma),
            )
        }

        fun from(row: Row, column: Column): Coordinate {
            return coordinates.find {
                it.row == row && it.column == column
            } ?: throw IllegalArgumentException()
        }
    }
}
