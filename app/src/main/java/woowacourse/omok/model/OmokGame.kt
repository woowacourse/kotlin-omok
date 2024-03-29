package woowacourse.omok.model

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.entity.Point
import woowacourse.omok.model.entity.StoneColor
import woowacourse.omok.model.turn.BlackTurn
import woowacourse.omok.model.turn.Finished
import woowacourse.omok.model.turn.Turn

class OmokGame(
    private var turn: Turn = BlackTurn(board = Board()),
) {
    fun run(
        inputPoint: () -> Pair<Int, Int>,
        beforeTurn: (Board, StoneColor) -> Unit,
        afterGame: (Board, StoneColor) -> Unit,
        onInappropriate: (String) -> Unit,
    ) {
        while (turn !is Finished) {
            beforeTurn(turn.board, turn.color())
            proceedTurn(inputPoint, onInappropriate)
        }
        afterGame(turn.board, turn.color())
    }

    private fun proceedTurn(
        inputPoint: () -> Pair<Int, Int>,
        onInappropriate: (String) -> Unit,
    ) {
        val point = inputPoint()
        turn = turn.placeStone(Point(point.first, point.second), onInappropriate)
    }
}
