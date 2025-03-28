package woowacourse.omok.view

import woowacourse.omok.domain.Position

object PositionParser {
    private const val ERROR_NOT_FIND: String = "유효하지 않은 입력입니다. 다시 입력해주세요."
    private val POSITION_PATTERN: Regex = """([A-Z]+)(\d+)""".toRegex()

    fun encode(input: String): Position {
        val matchResult =
            POSITION_PATTERN.matchEntire(input)
                ?: throw IllegalArgumentException(ERROR_NOT_FIND)
        val (rowString, column) = matchResult.destructured
        val row = RowType.valueOf(rowString).value
        val col = column.toInt() - 1
        return Position(row, col)
    }

    fun decode(position: Position): String {
        return "${RowType.from(position.x)}${position.y + 1}"
    }
}
