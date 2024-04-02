package woowacourse.omok.domain.omok.view

import woowacourse.omok.domain.omok.model.Board
import woowacourse.omok.domain.omok.model.Color
import woowacourse.omok.domain.omok.model.Position

class InputView {
    fun inputPosition(
        currentTurn: Color,
        board: Board,
    ): Pair<Int, Char> {
        showCurrentTurn(currentTurn, board)
        print(MESSAGE_INPUT_POSITION)
        return getPositionData()
    }

    private fun getPositionData(): Pair<Int, Char> {
        val position = readln()
        val col = position.first()
        val row = position.drop(1).toInt()
        return Pair(row, col)
    }

    private fun showCurrentTurn(
        currentTurn: Color,
        board: Board,
    ) {
        if (board.notation.isEmpty()) {
            println(MESSAGE_PLAYERS_TURN.format(currentTurn.label))
        }
        if (board.notation.isNotEmpty()) {
            println(MESSAGE_PLAYERS_TURN.format(currentTurn.label))
            val row = board.notation.last().rowCoordinate
            val col = board.notation.last().colCoordinate.toChar() + 'A'.code - 1
            showLastPosition(Position.of(row, col))
        }
    }

    private fun showLastPosition(position: Position) {
        println(MESSAGE_LAST_PLACE.format(position.col.title, position.row.title))
    }

    companion object {
        private const val MESSAGE_PLAYERS_TURN = "%s의 차례입니다."
        private const val MESSAGE_INPUT_POSITION = "위치를 입력하세요: "
        private const val MESSAGE_LAST_PLACE = "(마지막 돌의 위치: %s%s)"
    }
}
