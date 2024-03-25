package omok.model

import omok.model.board.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor
import omok.model.turn.BlackTurn
import omok.model.turn.Finished
import omok.model.turn.Turn

class OmokGame(
    private var turn: Turn = BlackTurn(board = Board()),
) {
    fun run(
        inputPoint: () -> Point,
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
        inputPoint: () -> Point,
        onInappropriate: (String) -> Unit,
    ) {
        turn = turn.placeStone(inputPoint(), onInappropriate)
    }
}
