package omok.model

import omok.model.entity.Point
import omok.model.entity.StoneColor
import omok.model.turn.BlackTurn
import omok.model.turn.Finished
import omok.model.turn.Turn

class OmokGame(
    val board: Board = Board(),
    private var turn: Turn = BlackTurn(board),
) {
    fun run(
        inputPoint: () -> Point,
        beforeTurn: (Board, StoneColor) -> Unit,
        afterGame: (Board, StoneColor) -> Unit,
    ) {
        while (turn !is Finished) {
            beforeTurn(board, turn.color())
            proceedTurn(inputPoint)
        }
        afterGame(board, turn.color())
    }

    private fun proceedTurn(inputPoint: () -> Point) {
        turn = turn.placeStone(inputPoint())
    }
}
