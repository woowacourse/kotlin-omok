package woowacourse.omok.domain

import woowacourse.omok.domain.Board.Companion.BOARD_SIZE

data class Position(val row: Int, val column: Int) {
    constructor(inputPosition: String) : this(parseToRow(inputPosition), parseToColumn(inputPosition))

    init {
        require(row in 0 until BOARD_SIZE && column in 0 until BOARD_SIZE) {
            throw IllegalArgumentException(ERROR_INVALID_PLACED.format(BOARD_SIZE))
        }
    }

    companion object {
        private val regex = """([A-Z]+)(\d+)""".toRegex()

        private fun parseToRow(inputPosition: String): Int {
            val matchResult = regex.matchEntire(inputPosition) ?: throw IllegalArgumentException()
            val (rowString, _) = matchResult.destructured
            val row =
                RowType.entries.find { it.name == rowString }?.value ?: throw IllegalArgumentException()
            return row
        }

        private fun parseToColumn(inputPosition: String): Int {
            val matchResult = regex.matchEntire(inputPosition) ?: throw IllegalArgumentException()
            val (_, columnString) = matchResult.destructured
            val column = columnString.toInt() - 1
            return column
        }

        private const val ERROR_INVALID_PLACED = "유효하지 않은 돌의 위치입니다. 위치는 A에서 O까지, 0 이상 %d 미만이어야 합니다."
    }
}
