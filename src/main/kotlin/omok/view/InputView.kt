package omok.view

import omok.model.Color
import omok.model.Position
import omok.model.Stone

class InputView {
    fun inputPosition(lastTurn: Stone?): Pair<Int, Char> {
        showCurrentTurn(lastTurn)
        print(MESSAGE_INPUT_POSITION)
        return getPositionData()
    }

    private fun getPositionData(): Pair<Int, Char> {
        val position = readln()
        val verticalCoordinate = position.first()
        val horizontalCoordinate = position.drop(1).toInt()
        return Pair(horizontalCoordinate, verticalCoordinate)
    }

    private fun showCurrentTurn(lastTurn: Stone?) {
        lastTurn?.let {
            val currentColor = Color.getReversedColor(it.color)
            println(MESSAGE_PLAYERS_TURN.format(getCurrentColor(currentColor)))
            showLastPosition(lastTurn.position)
        } ?: println(MESSAGE_PLAYERS_TURN.format(COLOR_BLACK))
    }

    private fun getCurrentColor(currentColor: Color): String =
        when (currentColor) {
            Color.BLACK -> COLOR_BLACK
            Color.WHITE -> COLOR_WHITE
        }

    private fun showLastPosition(position: Position) {
        println(MESSAGE_LAST_PLACE.format(position.verticalCoordinate.name, position.horizontalCoordinate.index))
    }

    companion object {
        private const val COLOR_BLACK = "흑"
        private const val COLOR_WHITE = "백"
        private const val MESSAGE_PLAYERS_TURN = "%s의 차례입니다."
        private const val MESSAGE_INPUT_POSITION = "위치를 입력하세요: "
        private const val MESSAGE_LAST_PLACE = "(마지막 돌의 위치: %s%d)"
    }
}
