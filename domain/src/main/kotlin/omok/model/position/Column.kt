package omok.model.position

import omok.model.board.Board

data class Column(val value: Int) {
    companion object {
        private const val EXCEPTION_INVALID_COLUMN = "유효하지 않은 y축 입니다. 현재 입력 값: %d\n"

        fun from(input: Int): Column {
            val value = input - 1
            invalidColumn(input)
            return Column(value)
        }

        private fun invalidColumn(value: Int) =
            require(value - 1 in Board.axisRange) {
                EXCEPTION_INVALID_COLUMN.format(value)
            }
    }
}
