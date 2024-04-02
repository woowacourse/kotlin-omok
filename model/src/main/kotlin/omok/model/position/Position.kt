package omok.model.position

data class Position(val row: Int, val column: Int) {
    init {
        validPosition(row, column)
    }

    fun convert() = "${START_ROW_VALUE + row}${COLUMN_OFFSET + column}"

    fun getRowValue() = START_ROW_VALUE + row

    fun getColumnValue() = column + COLUMN_OFFSET

    companion object {
        private const val START_ROW_VALUE = 'A'
        private const val APP_BOARD_SIZE = 15
        private const val COLUMN_OFFSET = 1
        private const val MIN_RANGE = 0
        private const val MAX_RANGE = 14

        private const val EXCEPTION_INVALID_POSITION = "유효하지 않은 위치입니다. 현재 입력 값: %s%d\n"

        private val positionRange = MIN_RANGE..MAX_RANGE

        private fun invalidPositionMessage(
            row: Int,
            column: Int,
        ): String {
            val rowValue = START_ROW_VALUE + row
            return EXCEPTION_INVALID_POSITION.format(rowValue, column + COLUMN_OFFSET)
        }

        fun fromIndex(index: Int): Position {
            val row = index % APP_BOARD_SIZE
            val column = APP_BOARD_SIZE - (index / APP_BOARD_SIZE) - COLUMN_OFFSET
            return Position(row, column)
        }

        fun of(
            rowValue: Char,
            columnValue: Int,
        ): Position {
            val row = rowValue - START_ROW_VALUE
            validPosition(row, columnValue - COLUMN_OFFSET)
            return Position(row, columnValue - COLUMN_OFFSET)
        }

        private fun validPosition(
            row: Int,
            column: Int,
        ) {
            require(row in positionRange && column in positionRange) {
                invalidPositionMessage(
                    row,
                    column,
                )
            }
        }
    }
}
