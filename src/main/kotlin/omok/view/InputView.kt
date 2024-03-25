package omok.view

import omok.model.Color
import omok.model.Point
import omok.model.Stone

class InputView {
    fun getPointOrNull(
        color: Color,
        lastStone: Stone?,
    ): Point? {
        println(HEADER_TURN.format(colorToString(color), convertPoint(lastStone)))
        print(HEADER_INPUT_POSITION)
        val input = readln().validation() ?: return null
        return input.toPointOrNull()
    }

    private fun String.validation(): String? = if (this.length !in 1..3) null else this

    private fun String.toPointOrNull(): Point? {
        val x: Int = convertX(this.first()) ?: return null
        val y: Int = convertY(this.substring(1, this.length)) ?: return null
        return Point(x, y)
    }

    private fun colorToString(color: Color): String {
        return when (color) {
            Color.BLACK -> COLOR_BLACK
            Color.WHITE -> COLOR_WHITE
            else -> COLOR_NONE
        }
    }

    private fun convertPoint(stone: Stone?): String {
        if (stone == null) return ""
        return " (마지막 돌의 위치: ${('A'.code + stone.point.row).toChar()}${stone.point.col + 1})"
    }

    private fun convertX(x: Char): Int? {
        val res = x.uppercaseChar().code - 'A'.code
        return if (res in 0..14) res else null
    }

    private fun convertY(y: String): Int? {
        return when {
            y.length !in 1..2 -> null
            y.toIntOrNull() == null -> null
            y.toInt() !in 1..15 -> null
            else -> y.toInt() - 1
        }
    }

    companion object {
        private const val HEADER_TURN = "%s의 차례입니다.%s"
        private const val HEADER_INPUT_POSITION = "위치를 입력하세요 : "

        private const val COLOR_BLACK = "흑"
        private const val COLOR_WHITE = "백"
        private const val COLOR_NONE = "NONE"

        private const val ERROR_INVALID_INPUT = "유효하지 않은 입력입니다. 행과 열의 값을 공백없이 입력해주세요! (ex. E14)"
        private const val ERROR_ROW_VALUE = "행 값은 알파벳 A~O 사이의 값이어야 합니다."
        private const val ERROR_COL_VALUE = "열 값은 숫자 1~15 사이의 값이어야 합니다."
    }
}
