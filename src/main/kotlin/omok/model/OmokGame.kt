package omok.model

import omok.model.entity.Point
import omok.model.entity.StoneColor
import omok.model.turn.BlackTurn
import omok.model.turn.Finished
import omok.model.turn.Turn

class OmokGame(
    board: Board = Board(),
    private var turn: Turn = BlackTurn(board),
) {
    fun run(
        inputPoint: () -> Point,
        beforeTurn: (Board, StoneColor) -> Unit,
        printError: (String) -> Unit,
        afterGame: (Board, StoneColor) -> Unit,
    ) {
        while (turn !is Finished) {
            beforeTurn(turn.board, turn.color())
            proceed(inputPoint, printError)
        }
        afterGame(turn.board, turn.color())
    }

    private fun proceed(
        inputPoint: () -> Point,
        printError: (String) -> Unit,
    ) {
        turn = turn.placeStone(inputPoint(), printError)
    }
}
