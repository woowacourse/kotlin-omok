package omok.model

import omok.model.entity.Point
import omok.model.entity.StoneColor
import omok.model.turn.BlackTurn
import omok.model.turn.Finished
import omok.model.turn.Turn

class OmokGame(
    board: Board = Board(),
    private var turn: Turn =
        BlackTurn(
            Either.Right(board),
        ),
) {
    fun run(
        inputPoint: () -> Point,
        printBoard: (Board, StoneColor) -> Unit,
        printError: (String) -> Unit,
        afterGame: (Board, StoneColor) -> Unit,
    ) {
        if (isFinished()) {
            justPrintBoard(afterGame, printError)
            return
        }
        proceedTurn(
            printBoard,
            printError,
            inputPoint,
        )
    }

    fun isFinished() = turn is Finished

    private fun proceedTurn(
        printBoard: (Board, StoneColor) -> Unit,
        printInterruptMessage: (String) -> Unit,
        inputPoint: () -> Point,
    ) {
        val nextTurn = turn.proceed(inputPoint())
        nextTurn.board.valueOf(
            onLeft = {
                printInterruptMessage(it.errorMessage)
                if (it is PlaceStoneInterrupt.GameFinished) {
                    turn = Finished(it.board)
                }
            },
            onRight = {
                printBoard(it, turn.color())
                turn = nextTurn
            },
        )
    }

    fun justPrintBoard(
        printBoard: (Board, StoneColor) -> Unit,
        printError: (String) -> Unit,
    ) = turn.board.valueOf(
        onLeft = {
            printError(it.errorMessage)
        },
        onRight = {
            printBoard(it, turn.color())
        },
    )
}
