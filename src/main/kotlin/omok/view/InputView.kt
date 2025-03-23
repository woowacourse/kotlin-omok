package omok.view

import omok.model.Color
import omok.model.Game
import omok.model.Position
import omok.model.Stone

class InputView {
    fun readTurn(game: Game): Pair<Int, Int> {
        promptInput(game)
        val input: String = readln()
        require(input.isNotBlank()) { ERROR_MESSAGE_INCORRECT_POSITION_FORMAT }
        val col: Int = readCol(input)
        val row: Int = readRow(input)
        return col to row
    }

    private fun promptInput(game: Game) {
        val lastStone: Stone? = game.lastStone
        val playerName: String =
            when (game.lastStone?.color) {
                Color.BLACK -> WHITE_PLAYER
                Color.WHITE, null -> BLACK_PLAYER
            }
        print(MESSAGE_TURN_INDICATOR.format(playerName))
        println(
            when (lastStone) {
                null -> ""
                else -> MESSAGE_LAST_STONE_POSITION.format(lastStone.position.stringRepresentation())
            },
        )
        print(MESSAGE_ENTER_POSITION)
    }

    private fun readCol(input: String): Int {
        return input.uppercase()[0].code - ASCII_OFFSET
    }

    private fun readRow(input: String): Int {
        return input.substring(1).toIntOrNull() ?: throw IllegalArgumentException(ERROR_MESSAGE_INCORRECT_POSITION_FORMAT)
    }

    private fun Position.stringRepresentation(): String {
        return "${(this.x + ASCII_OFFSET).toChar()}${this.y}"
    }

    companion object {
        const val MESSAGE_TURN_INDICATOR = "%s의 차례입니다. "
        const val MESSAGE_LAST_STONE_POSITION = "(마지막 돌의 위치: %s)"
        const val MESSAGE_ENTER_POSITION = "위치를 입력하세요: "

        private const val BLACK_PLAYER = "흑"
        private const val WHITE_PLAYER = "백"
        private const val ERROR_MESSAGE_INCORRECT_POSITION_FORMAT = "올바르지 않은 위치 입력 형식입니다."
        private const val ASCII_OFFSET = 'A'.code - 1
    }
}
