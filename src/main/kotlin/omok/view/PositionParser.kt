package omok.view

import omok.domain.Position
import omok.domain.RowType

class PositionParser {
    fun parse(input: String): Position {
        val regex = """([A-Z]+)(\d+)""".toRegex()
        val matchResult = regex.matchEntire(input) ?: throw IllegalArgumentException(ERROR_NOT_FIND)

        val (rowString, column) = matchResult.destructured
        val row = RowType.valueOf(rowString).value
        val col = column.toInt() - 1
        return Position(row, col)
    }

    companion object {
        private const val ERROR_NOT_FIND = "유효하지 않은 입력입니다. 다시 입력해주세요."
    }
}
