package omok.domain

import omok.domain.Board.Companion.BOARD_SIZE

data class Position private constructor(val row: Int, val column: Int) {
    companion object {
        fun from(inputPosition: String): Position? {
            val regex = """([A-Z]+)(\d+)""".toRegex()
            val matchResult = regex.matchEntire(inputPosition) ?: return null
            val (rowString, columnString) = matchResult.destructured
            val row =
                RowType.entries.find { it.name == rowString }?.value ?: return null
            val column = columnString.toInt() - 1
            return from(row, column)
        }

        fun from(row: Int, column: Int): Position {
            validateStoneRange(row, column)
            return Position(row, column)
        }

        private fun validateStoneRange(row: Int, column: Int) {
            if (row !in 0 until BOARD_SIZE || column !in 0 until BOARD_SIZE) {
                throw IllegalArgumentException(ERROR_INVALID_PLACED.format(BOARD_SIZE))
            }
        }

        private const val ERROR_INVALID_PLACED = "유효하지 않은 돌의 위치입니다. 오목판은 0 이상 %d 미만이어야 합니다."
    }
}
