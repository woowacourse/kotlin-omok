package omok.view

import omok.domain.OmokGrid
import omok.domain.Position
import omok.domain.player.Player

class InputView {
    fun getPoint(
        player: Player,
        latestPoint: Position?,
        grid: OmokGrid,
    ): Position {
//        print(MESSAGE_TURN.format(player.getDisplayColor()))
        if (latestPoint != null) print(MESSAGE_LATEST_POSITION.format(convertToString(latestPoint)))
        print(MESSAGE_POSITION_GUIDE)
        val rawInput = readln().trim()

        return parsingInput(rawInput, grid) ?: getPoint(player, latestPoint, grid)
    }

    companion object {
        private const val MESSAGE_TURN: String = "\n%s의 차례입니다."
        private const val MESSAGE_LATEST_POSITION: String = "(마지막 돌의 위치: %s)"
        private const val MESSAGE_POSITION_GUIDE: String = "\n위치를 입력하세요: "

        private fun convertToString(point: Position): String {
            val letter = 'A' + point.x - 1
            return letter + (point.y).toString()
        }

        private fun parsingInput(
            rawInput: String,
            grid: OmokGrid,
        ): Position? {
            if (rawInput.isEmpty()) return null

            val rawRow = rawInput.substring(1)
            val rawCol = rawInput.substring(0, 1)

            val row = validateRow(rawRow, grid.height) ?: return null
            val col = validateCol(rawCol, grid.width) ?: return null
            return Position(row, col)
        }

        private fun validateRow(
            rawRow: String,
            height: Int,
        ): Int? {
            if (rawRow.toIntOrNull() == null) return null
            if (rawRow.toInt() !in 1..height) return null
            return rawRow.toInt()
        }

        private fun validateCol(
            rawCol: String,
            width: Int,
        ): Int? {
            val convertedCol = convertLetter(rawCol)
            if (convertedCol !in 1..width) return null
            return convertedCol
        }

        private fun convertLetter(letter: String): Int {
            return letter[0] - 'A' + 1
        }
    }
}
