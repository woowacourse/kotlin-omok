package omok.view

import omok.model.Color
import omok.model.Game2
import omok.model.PointFactory
import omok.model.Stone
import rule.wrapper.point.Point

class InputView {
    fun readInitialTurn(): Point {
        println(MESSAGE_INITIAL_TURN_INDICATOR)
        return readPoint()
    }

    fun readTurn(lastStone: Stone): Point {
        val currentColor: Color = lastStone.color.reverse()
        print(
            MESSAGE_TURN_INDICATOR.format(
                when (currentColor) {
                    Color.WHITE -> "백"
                    Color.BLACK -> "흑"
                },
            ),
        )
        println(MESSAGE_LAST_STONE_POINT.format(lastStone.point.stringRepresentation()))
        return readPoint()
    }

    fun readTurn2(game: Game2): Pair<Int, Int> {
        val playerName: String =
            when (game.lastColor) {
                Color.BLACK -> "백"
                Color.WHITE, null -> "흑"
            }
        println(MESSAGE_TURN_INDICATOR.format(playerName))

        val input: String = readln().trim()
        val col: Int = readCol(input)
        val row: Int = readRow(input)
        return row to col
    }

    private fun readCol(input: String): Int {
        return input.uppercase()[0].code - ASCII_OFFSET
    }

    private fun readRow(input: String): Int {
        return input.substring(1).toIntOrNull() ?: throw IllegalArgumentException(ERROR_MESSAGE_INCORRECT_POSITION_FORMAT)
    }

    private fun readPoint(): Point {
        return runCatching {
            print(MESSAGE_ENTER_POINT)
            val input: String = readln()
            val col: Int = input[0].integerRepresentation()
            val row: Int? = input.substring(1).toIntOrNull()
            PointFactory.create(row, col)
        }.getOrElse {
            println(it.message)
            readPoint()
        }
    }

    private fun Char.integerRepresentation(): Int {
        return this.uppercase()[0].code - ASCII_OFFSET
    }

    private fun Point.stringRepresentation(): String {
        return "${(this.col + ASCII_OFFSET).toChar()}${this.row}"
    }

    companion object {
        const val MESSAGE_TURN_INDICATOR = "%s의 차례입니다. "
        const val MESSAGE_INITIAL_TURN_INDICATOR = "흑의 차례입니다."
        const val MESSAGE_LAST_STONE_POINT = "(마지막 돌의 위치: %s)"
        const val MESSAGE_ENTER_POINT = "위치를 입력하세요: "

        private const val ERROR_MESSAGE_INCORRECT_POSITION_FORMAT = "올바르지 않은 위치 입력 형식입니다."
        private const val ASCII_OFFSET = 'A'.code - 1
    }
}
