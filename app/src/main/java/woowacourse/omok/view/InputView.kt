package woowacourse.omok.view

import woowacourse.omok.domain.StoneColor
import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.grid.Row
import woowacourse.omok.domain.grid.Stone

class InputView {
    fun getPoint(
        stoneColor: StoneColor,
        latestStone: Stone?,
    ): Point {
        print(MESSAGE_TURN.format(stoneColor.getDisplayColor()))
        if (latestStone != null) print(MESSAGE_LATEST_POSITION.format(convertToString(latestStone)))
        print(MESSAGE_POSITION_GUIDE)
        val rawInput = readln().trim()

        return parsingInput(rawInput) ?: getPoint(stoneColor, latestStone)
    }

    private fun convertToString(stone: Stone): String {
        val letter = COORDINATE_Y_START_CHAR + stone.point.col.value - INDEX_OFFSET
        return letter + (stone.point.row.value).toString()
    }

    private fun parsingInput(rawInput: String): Point? {
        if (rawInput.isEmpty()) return null

        val rawRow = rawInput.substring(CUTTING_STANDARD)
        val rawCol = rawInput.substring(START_INDEX, CUTTING_STANDARD)

        val row = validateRow(rawRow) ?: return null
        val col = validateCol(rawCol) ?: return null
        return Point(Row(row), Column(col))
    }

    private fun validateRow(row: String): Int? {
        if (row.toIntOrNull() == null) return null
        return row.toInt()
    }

    private fun validateCol(col: String): Int? {
        if (col[0] !in COORDINATE_Y_START_CHAR..COORDINATE_Y_END_CHAR) return null
        return convertLetter(col)
    }

    private fun convertLetter(letter: String): Int {
        return letter[0] - COORDINATE_Y_START_CHAR + INDEX_OFFSET
    }

    private fun StoneColor.getDisplayColor(): String {
        return when (this) {
            StoneColor.BLACK -> "흑"
            StoneColor.WHITE -> "백"
        }
    }

    companion object {
        private const val MESSAGE_TURN: String = "\n%s의 차례입니다."
        private const val MESSAGE_LATEST_POSITION: String = "(마지막 돌의 위치: %s)"
        private const val MESSAGE_POSITION_GUIDE: String = "\n위치를 입력하세요: "

        private const val START_INDEX: Int = 0
        private const val CUTTING_STANDARD: Int = 1
        private const val INDEX_OFFSET: Int = 1

        private const val COORDINATE_Y_START_CHAR: Char = 'A'
        private const val COORDINATE_Y_END_CHAR: Char = 'Z'
    }
}
