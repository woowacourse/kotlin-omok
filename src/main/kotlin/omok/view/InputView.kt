package omok.view

import omok.model.Color
import omok.model.Point
import omok.model.Stone
import omok.retryWhileNotException

class InputView {
    fun getPoint(
        color: Color,
        lastStone: Stone?,
    ): Point =
        retryWhileNotException {
            println("${colorToString(color)}의 차례입니다.${convertPosition(lastStone)}")
            print("위치를 입력하세요 : ")
            val input = readln()
            val x: Int = convertX(input.first()) ?: throw IllegalArgumentException("행 값 오류")
            val y: Int = convertY(input.substring(1, input.length)) ?: throw IllegalArgumentException("열 값 오류")
            Point(x, y)
        }

    private fun colorToString(color: Color): String {
        return when (color) {
            Color.BLACK -> "흑"
            Color.WHITE -> "백"
        }
    }

    private fun convertPosition(stone: Stone?): String {
        if (stone == null) return ""
        return " (마지막 돌의 위치: ${('A'.code + stone.point.row).toChar()}${stone.point.col + 1})"
    }

    private fun convertX(x: Char): Int? {
        val res = x.uppercaseChar().code - 'A'.code
        return if (res in 0..14) {
            res
        } else {
            null
        }
    }

    private fun convertY(y: String): Int? {
        return when {
            y.length !in 1..2 -> null
            y.toIntOrNull() == null -> null
            y.toInt() !in 1..15 -> null
            else -> y.toInt() - 1
        }
    }
}
