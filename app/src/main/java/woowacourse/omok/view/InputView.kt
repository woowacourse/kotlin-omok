package woowacourse.omok.view

import woowacourse.omok.model.Color
import woowacourse.omok.model.Game
import woowacourse.omok.model.Stone
import woowacourse.omok.model.position.Col
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.position.Row

class InputView {
    fun readTurn(game: Game): Position {
        promptInput(game)
        val input: String = readln()
        println()
        require(input.isNotBlank()) { ERROR_MESSAGE_INCORRECT_POSITION_FORMAT }
        val col: Col = readCol(input)
        val row: Row = readRow(input)
        return Position(col, row)
    }

    private fun promptInput(game: Game) {
        val lastStone: Stone? = game.lastStone
        val playerName: String =
            when (lastStone?.color) {
                Color.BLACK -> WHITE_PLAYER
                Color.WHITE, null -> BLACK_PLAYER
            }
        print(MESSAGE_TURN_INDICATOR.format(playerName))
        if (lastStone != null) print(MESSAGE_LAST_STONE_POSITION.format(lastStone.position.stringRepresentation()))
        println()
        print(MESSAGE_ENTER_POSITION)
    }

    private fun readCol(input: String): Col {
        return Col(input.uppercase()[0].code - ASCII_OFFSET)
    }

    private fun readRow(input: String): Row {
        return Row(input.substring(1).toIntOrNull() ?: throw IllegalArgumentException(ERROR_MESSAGE_INCORRECT_POSITION_FORMAT))
    }

    private fun Position.stringRepresentation(): String {
        return "${(this.x.value + woowacourse.omok.view.InputView.ASCII_OFFSET).toChar()}${this.y.value}"
    }

    companion object {
        private const val ASCII_OFFSET = 'A'.code - 1

        private const val BLACK_PLAYER = "흑"
        private const val WHITE_PLAYER = "백"
        private const val MESSAGE_TURN_INDICATOR = "%s의 차례입니다. "
        private const val MESSAGE_LAST_STONE_POSITION = "(마지막 돌의 위치: %s)"
        private const val MESSAGE_ENTER_POSITION = "위치를 입력하세요: "
        private const val ERROR_MESSAGE_INCORRECT_POSITION_FORMAT = "올바르지 않은 위치 입력 형식입니다."
    }
}
