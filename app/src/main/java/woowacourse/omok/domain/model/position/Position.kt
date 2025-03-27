package omok.domain.model.position

data class Position(val column: Column, val row: Row) {
    companion object {
        private const val POSITION_ERROR_MESSAGE = "[ERROR] 위치가 오목판의 범위를 벗어났습니다."

        fun of(
            columnValue: Int,
            rowValue: Int,
            size: Int,
        ): Position {
            require(columnValue <= size && rowValue <= size) {
                POSITION_ERROR_MESSAGE
            }
            val column = Column(columnValue)
            val row = Row(rowValue)
            return Position(column, row)
        }
    }
}
