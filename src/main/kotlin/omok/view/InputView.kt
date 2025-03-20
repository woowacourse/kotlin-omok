package omok.view

import omok.domain.OmokGrid.Companion.DEFAULT_SIZE
import omok.domain.player.BlackPlayer
import omok.domain.player.Player
import omok.domain.player.WhitePlayer
import rule.wrapper.point.Point

class InputView {
    fun getPoint(
        player: Player,
        latestPoint: Point?,
    ): Point {
        print(MESSAGE_TURN.format(player.getDisplayColor()))
        if (latestPoint != null) print(MESSAGE_LATEST_POSITION.format(convertToString(latestPoint)))
        print(MESSAGE_POSITION_GUIDE)
        val rawInput = readln().trim()

        return parsingInput(rawInput) ?: getPoint(player, latestPoint)
    }

    private fun convertToString(point: Point): String {
        val letter = 'A' + point.col - 1
        return letter + (point.row).toString()
    }

    private fun parsingInput(rawInput: String): Point? {
        if (rawInput.isEmpty()) return null

        val rawRow = rawInput.substring(1)
        val rawCol = rawInput.substring(0, 1)

        val row = validateRow(rawRow) ?: return null
        val col = validateCol(rawCol) ?: return null
        return Point(row, col)
    }

    private fun validateRow(number: String): Int? {
        if (number.toIntOrNull() == null) return null
        if (number.toInt() !in 1..DEFAULT_SIZE) return null
        return number.toInt()
    }

    private fun validateCol(col: String): Int? {
        if (col[0] - 'A' !in 0 until DEFAULT_SIZE) return null
        return convertLetter(col)
    }

    private fun convertLetter(letter: String): Int {
        return letter[0] - 'A' + 1
    }

    private fun Player.getDisplayColor(): String {
        return when (this) {
            is BlackPlayer -> "흑"
            is WhitePlayer -> "백"
            else -> throw IllegalArgumentException()
        }
    }

    companion object {
        private const val MESSAGE_TURN: String = "\n%s의 차례입니다."
        private const val MESSAGE_LATEST_POSITION: String = "(마지막 돌의 위치: %s)"
        private const val MESSAGE_POSITION_GUIDE: String = "\n위치를 입력하세요: "
    }
}
