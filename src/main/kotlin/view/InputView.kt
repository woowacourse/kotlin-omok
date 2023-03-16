package view

import Stone
import domain.stone.Color
import domain.stone.Column
import domain.stone.Position
import domain.stone.Row

class InputView {

    fun requestPoint(stone: Stone?): Position {
        println(TURN_MESSAGE.format(getNextColorName(stone?.color), getLatestPoint(stone?.position)))
        print(REQUEST_POINT_MESSAGE)
        val input = readln()
        return runCatchingOrNull {
            val x = Column.valueOf(convertCharToX(input[0]) - 1)
            val y = Row.valueOf(input.substring(1).toInt() - 1)
            Position(x, y)
        } ?: requestPoint(stone)
    }

    private fun convertCharToX(char: Char): Int = char.code - CONVERTING_BASE_NUMBER
    private fun convertXtoChar(x: Int): Char = (CONVERTING_BASE_NUMBER + x).toChar()

    private fun getNextColorName(curTurnColor: Color?): String = when (curTurnColor) {
        null, Color.WHITE -> BLACK
        Color.BLACK -> WHITE
    }

    private fun getLatestPoint(position: Position?) = when (position) {
        null -> EMPTY_STRING
        else -> LAST_STONE_POINT_MESSAGE.format(convertXtoChar(position.column.x + 1), position.row.y + 1)
    }

    private fun <T> runCatchingOrNull(block: () -> T?): T? {
        return kotlin.runCatching {
            block()
        }.onFailure {
            println(it.message)
        }.getOrNull()
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
