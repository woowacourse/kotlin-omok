package view

import woowacourse.omok.domain.OmokBoard.Companion.DEFAULT_SIZE
import woowacourse.omok.domain.Position

class InputView {
    fun getPosition(latestPosition: Position?): Position {
        if (latestPosition != null) print(MESSAGE_LATEST_POSITION.format(convertToString(latestPosition)))
        print(MESSAGE_POSITION_GUIDE)
        val rawInput = readln().trim()

        return parsingInput(rawInput) ?: getPosition(latestPosition)
    }

    companion object {
        private const val MESSAGE_LATEST_POSITION: String = " (마지막 돌의 위치: %s)"
        private const val MESSAGE_POSITION_GUIDE: String = "\n위치를 입력하세요: "

        private fun convertToString(position: Position): String {
            val letter = 'A' + position.x
            return letter + (position.y + 1).toString()
        }

        private fun parsingInput(rawInput: String): Position? {
            if (rawInput.isEmpty()) return null

            val rawX = rawInput.substring(0, 1)
            val rawY = rawInput.substring(1)

            val x = validateX(rawX) ?: return null
            val y = validateY(rawY) ?: return null
            return Position(x, y)
        }

        private fun validateX(rawX: String): Int? {
            val convertedCol = convertLetter(rawX)
            if (convertedCol !in 1..DEFAULT_SIZE) return null
            return convertedCol - 1
        }

        private fun validateY(rawY: String): Int? {
            if (rawY.toIntOrNull() == null) return null
            if (rawY.toInt() !in 1..DEFAULT_SIZE) return null
            return rawY.toInt() - 1
        }

        private fun convertLetter(letter: String): Int = letter[0] - 'A' + 1
    }
}
