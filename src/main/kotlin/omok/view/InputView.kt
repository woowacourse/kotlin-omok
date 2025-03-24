package omok.view

import omok.domain.StoneColor
import omok.domain.grid.Column
import omok.domain.grid.OmokGrid.Companion.DEFAULT_SIZE
import omok.domain.grid.OmokPoint
import omok.domain.grid.Row

class InputView {
    fun getPoint(
        stoneColor: StoneColor,
        latestPoint: OmokPoint?,
    ): OmokPoint {
        print(MESSAGE_TURN.format(stoneColor.getDisplayColor()))
        if (latestPoint != null) print(MESSAGE_LATEST_POSITION.format(convertToString(latestPoint)))
        print(MESSAGE_POSITION_GUIDE)
        val rawInput = readln().trim()

        return parsingInput(rawInput) ?: getPoint(stoneColor, latestPoint)
    }

    private fun convertToString(point: OmokPoint): String {
        val letter = 'A' + point.col.value - INDEX_OFFSET
        return letter + (point.row.value).toString()
    }

    private fun parsingInput(rawInput: String): OmokPoint? {
        if (rawInput.isEmpty()) return null

        val rawRow = rawInput.substring(CUTTING_STANDARD)
        val rawCol = rawInput.substring(START_INDEX, CUTTING_STANDARD)

        val row = validateRow(rawRow) ?: return null
        val col = validateCol(rawCol) ?: return null
        return OmokPoint(Row(row), Column(col))
    }

    private fun validateRow(row: String): Int? {
        if (row.toIntOrNull() == null) return null
        if (row.toInt() !in MIN_BOUND..DEFAULT_SIZE) return null
        return row.toInt()
    }

    private fun validateCol(col: String): Int? {
        val convertedCol = convertLetter(col)
        if (convertedCol !in MIN_BOUND..DEFAULT_SIZE) return null
        return convertedCol
    }

    private fun convertLetter(letter: String): Int {
        return letter[0] - 'A' + INDEX_OFFSET
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
        private const val MIN_BOUND: Int = 1
        private const val START_INDEX: Int = 0
        private const val CUTTING_STANDARD: Int = 1
        private const val INDEX_OFFSET: Int = 1
    }
}
