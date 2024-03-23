package omok.view

import omok.model.Position
import omok.model.Stone

fun Stone.output(): String = if (this == Stone.BLACK) "흑" else "백"

fun Position.output(): String {
    val colName = (this.col + 'A'.code).toChar()
    val rowName = Position.MAX_INDEX - this.row + 1
    return "$colName$rowName"
}

fun String.toPosition(): Position {
    require(isNotBlank()) { BLANK_MESSAGE }
    require(substring(1, length).toIntOrNull() in 1..15) { INVALID_ROW_MESSAGE }
    require(this[0] in 'A'..'O') { INVALID_COL_MESSAGE }

    val row = Position.MAX_INDEX - substring(1, length).toInt() + 1
    val col = this[0] - 'A'
    return Position(row, col)
}

private const val BLANK_MESSAGE = "공백을 입력하셨습니다. 다시 입력해 주세요."
private const val INVALID_ROW_MESSAGE = "올바르지 않은 행입니다."
private const val INVALID_COL_MESSAGE = "올바르지 않은 열입니다."
