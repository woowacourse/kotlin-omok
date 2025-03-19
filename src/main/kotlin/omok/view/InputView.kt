package omok.view

import omok.domain.StoneState
import rule.wrapper.point.Point

class InputView {
    fun getPoint(
        turn: StoneState,
        latestPoint: Point?,
    ): Point {
        print(MESSAGE_TURN.format(turn.getDisplayColor()))
        if (latestPoint != null) print(MESSAGE_LATEST_POSITION.format(convertToString(latestPoint)))
        print(MESSAGE_POSITION_GUIDE)
        val rawInput = readln().trim()

        return validateInput(rawInput) ?: getPoint(turn, latestPoint)
    }

    private fun convertToString(point: Point): String {
        val letter = 'A' + point.col
        return letter + (point.row + 1).toString()
    }

    private fun validateInput(rawInput: String): Point? {
        val match: MatchResult = REGEX_PATTERN.matchEntire(rawInput) ?: return null
        val (letter, number) = match.destructured
        return Point(number.toInt(), convertLetter(letter))
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

    companion object {
        private const val MESSAGE_TURN: String = "%s의 차례입니다."
        private const val MESSAGE_LATEST_POSITION: String = "(마지막 돌의 위치: %s)"
        private const val MESSAGE_POSITION_GUIDE: String = "\n위치를 입력하세요: "
        private val REGEX_PATTERN = Regex("^([A-O])([1-9]|1[0-5])$")
    }
}
