package omok.view

import omok.domain.StoneState
import omok.domain.point.Column
import omok.domain.point.OmokPoint
import omok.domain.point.Row

class InputView {
    fun getPoint(
        stoneColor: StoneState,
        latestPoint: OmokPoint?,
    ): OmokPoint {
        print(MESSAGE_TURN.format(stoneColor.getDisplayColor()))
        if (latestPoint != null) print(MESSAGE_LATEST_POSITION.format(convertToString(latestPoint)))
        print(MESSAGE_POSITION_GUIDE)
        val rawInput = readln().trim()

        return parsingInput(rawInput) ?: getPoint(stoneColor, latestPoint)
    }

    companion object {
        private const val MESSAGE_TURN: String = "\n%s의 차례입니다."
        private const val MESSAGE_LATEST_POSITION: String = "(마지막 돌의 위치: %s)"
        private const val MESSAGE_POSITION_GUIDE: String = "\n위치를 입력하세요: "

        private fun convertToString(point: OmokPoint): String {
            val letter = 'A' + point.col.value - 1
            return letter + (point.row).toString()
        }

        private fun parsingInput(rawInput: String): OmokPoint? {
            if (rawInput.isEmpty()) return null

            val rawRow = rawInput.substring(1)
            val rawCol = rawInput.substring(0, 1)

            val row = validateRow(rawRow, 15) ?: return null
            val col = validateCol(rawCol, 15) ?: return null
            return OmokPoint(Row(row), Column(col))
        }

        private fun validateRow(
            row: String,
            height: Int,
        ): Int? {
            if (row.toIntOrNull() == null) return null
            if (row.toInt() !in 1..height) return null
            return row.toInt()
        }

        private fun validateCol(
            col: String,
            width: Int,
        ): Int? {
            val convertedCol = convertLetter(col)
            if (convertedCol !in 1..width) return null
            return convertedCol
        }

        private fun convertLetter(letter: String): Int {
            return letter[0] - 'A' + 1
        }

        private fun StoneState.getDisplayColor(): String {
            return when (this) {
                StoneState.BLACK -> "흑"
                StoneState.WHITE -> "백"
                else -> throw IllegalArgumentException()
            }
        }
    }
}
