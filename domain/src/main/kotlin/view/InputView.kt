package view

import domain.stone.Color
import domain.stone.Column
import domain.stone.Position
import domain.stone.Row

class InputView {

    fun requestPoint(turnColor: Color, latestPosition: Position?): Position {
        println(TURN_MESSAGE.format(stoneColorKorean(turnColor), getLatestPoint(latestPosition)))
        print(REQUEST_POINT_MESSAGE)
        val input = readln()
        return runCatchingOrNull {
            val x = Column.valueOf(convertCharToX(input[0]) - 1)
            val y = Row.valueOf(input.substring(1).toInt() - 1)
            Position(x, y)
        } ?: requestPoint(turnColor, latestPosition)
    }

    private fun convertCharToX(char: Char): Int = char.code - CONVERTING_BASE_NUMBER

    private fun getLatestPoint(position: Position?) = when (position) {
        null -> EMPTY_STRING
        else -> LAST_STONE_POINT_MESSAGE.format(
            (Column.values().indexOf(position.column) + CONVERTING_BASE_NUMBER + 1).toChar(),
            Row.values().indexOf(position.row) + 1
        )
    }

    private fun <T> runCatchingOrNull(block: () -> T?): T? {
        return kotlin.runCatching {
            block()
        }.onFailure {
            println(it.message)
        }.getOrNull()
    }

    private fun stoneColorKorean(color: Color): String =
        when (color) {
            Color.BLACK -> BLACK
            Color.WHITE -> WHITE
        }

    companion object {
        private const val TURN_MESSAGE = "%s의 차례입니다. %s"
        private const val REQUEST_POINT_MESSAGE = "위치를 입력하세요: "
        private const val EMPTY_STRING = ""
        private const val LAST_STONE_POINT_MESSAGE = "(마지막 돌의 위치:%c%d)"
        private const val CONVERTING_BASE_NUMBER = 64
        private const val BLACK = "흑"
        private const val WHITE = "백"
    }
}
