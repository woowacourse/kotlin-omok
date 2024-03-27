package omok.view

import omok.model.Board
import omok.model.Color
import omok.model.Point
import omok.model.Stone

class InputView {
    fun getPointOrNull(
        color: Color,
        board: Board,
    ): Point {
        println(HEADER_TURN.format(colorToString(color), convertPoint(board.lastStone())))
        print(HEADER_INPUT_POSITION)
        val input = readln().validation()
        return input.toPointOrNull(board.size)
    }

    private fun String.validation(): String {
        require(this.length in 1..3) { ERROR_INVALID_INPUT }
        return this
    }

    private fun String.toPointOrNull(max: Int): Point {
        val x: Int = convertX(this.first(), max)
        val y: Int = convertY(this.substring(1, this.length), max)
        return Point(x, y)
    }

    private fun colorToString(color: Color): String {
        return when (color) {
            Color.BLACK -> COLOR_BLACK
            Color.WHITE -> COLOR_WHITE
        }
    }

    private fun convertPoint(stone: Stone?): String {
        if (stone == null) return ""
        return " (마지막 돌의 위치: ${('A'.code + stone.point.row).toChar()}${stone.point.col + 1})"
    }

    private fun convertX(
        x: Char,
        max: Int,
    ): Int {
        val res = x.uppercaseChar().code - 'A'.code
        require(res in MIN_POINT until max) { ERROR_ROW_VALUE }
        return res
    }

    private fun convertY(
        y: String,
        max: Int,
    ): Int {
        require(y.toIntOrNull() != null && y.toInt() - 1 in MIN_POINT until max) { ERROR_COL_VALUE }
        return y.toInt() - 1
    }

    companion object {
        private const val HEADER_TURN = "%s의 차례입니다.%s"
        private const val HEADER_INPUT_POSITION = "위치를 입력하세요 : "

        private const val COLOR_BLACK = "흑"
        private const val COLOR_WHITE = "백"

        private const val MIN_POINT = 0

        private const val ERROR_INVALID_INPUT = "유효하지 않은 입력입니다. 행과 열의 값을 공백없이 입력해주세요! (ex. E14)"
        private const val ERROR_ROW_VALUE = "행 값은 알파벳 A~O 사이의 값이어야 합니다."
        private const val ERROR_COL_VALUE = "열 값은 숫자 1~15 사이의 값이어야 합니다."
    }
}
