package omok.model

import omok.model.entity.Point
import omok.model.entity.StoneColor
import omok.model.turn.BlackTurn
import omok.model.turn.Finished
import omok.model.turn.Turn

class OmokGame(
    board: Board = Board(),
    private val turn: Either<PlaceStoneError, Turn> = Either.Right(BlackTurn(board)),
) {
    fun run(
        inputPoint: () -> Point,
        beforeTurn: (Board, StoneColor) -> Unit,
        printError: (String) -> Unit,
        afterGame: (Board, StoneColor) -> Unit,
    ) {
        proceed(
            turn,
            inputPoint,
            beforeTurn,
            printError,
            afterGame,
        )
    }

    private tailrec fun proceed(
        turn: Either<PlaceStoneError, Turn>,
        inputPoint: () -> Point,
        beforeTurn: (Board, StoneColor) -> Unit,
        printError: (String) -> Unit,
        afterGame: (Board, StoneColor) -> Unit,
    ) {
        val newTurn =
            when (val nowTurn = turn) {
                is Either.Left -> {
                    printError(nowTurn.value.errorMessage)
                    return
                }

                is Either.Right -> {
                    beforeTurn(nowTurn.value.board, nowTurn.value.color())
                    nowTurn.value.placeStone(inputPoint())
                }
            }
        if (newTurn is Either.Right && newTurn.value is Finished) {
            afterGame(newTurn.value.board, newTurn.value.color())
            return
        }
        proceed(newTurn, inputPoint, beforeTurn, printError, afterGame)
    }
}
