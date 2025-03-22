package omok.view

import omok.domain.Position

class InputView {
    fun getPosition(latestPosition: Position?): Position {
        if (latestPosition != null) print(MESSAGE_LATEST_POSITION.format(convertToString(latestPosition)))
        print(MESSAGE_POSITION_GUIDE)
        val rawInput = readln().trim()

        return parsingInput(rawInput) ?: getPosition(latestPosition)
    }

    companion object {
        private const val MESSAGE_TURN: String = "\n%s의 차례입니다."
        private const val MESSAGE_LATEST_POSITION: String = "(마지막 돌의 위치: %s)"
        private const val MESSAGE_POSITION_GUIDE: String = "\n위치를 입력하세요: "

        private fun convertToString(position: Position): String {
            val letter = 'A' + position.y - 1
            return letter + (position.x).toString()
        }

        private fun parsingInput(rawInput: String): Position? {
            if (rawInput.isEmpty()) return null

            val rawRow = rawInput.substring(1)
            val rawCol = rawInput.substring(0, 1)

            val row = validateRow(rawRow) ?: return null
            val col = validateCol(rawCol) ?: return null
            return Position(row, col)
        }

        private fun validateRow(rawRow: String): Int? {
            if (rawRow.toIntOrNull() == null) return null
            if (rawRow.toInt() !in 1..15) return null
            return rawRow.toInt()
        }

        private fun validateCol(rawCol: String): Int? {
            val convertedCol = convertLetter(rawCol)
            if (convertedCol !in 1..15) return null
            return convertedCol
        }

        private fun convertLetter(letter: String): Int {
            return letter[0] - 'A' + 1
        }
    }
}
