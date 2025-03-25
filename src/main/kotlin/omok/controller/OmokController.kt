package omok.controller

import omok.model.Board
import omok.model.Game
import omok.model.MoveResult
import omok.model.Rule
import omok.model.position.Position
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        outputView.printOmokStart()
        val game = Game(Board(), Rule())
        outputView.printBoard(game.board)
        retryOnError { processTurn(game) }
    }

    private tailrec fun processTurn(game: Game) {
        val position: Position = inputView.readTurn(game)
        val moveResult: MoveResult = game.processTurn(position, game.chooseTurn())
        outputView.printBoard(game.board)

        when (moveResult) {
            is MoveResult.Success.Playing -> processTurn(game)
            is MoveResult.Success.Finished -> {
                outputView.printMoveResult(moveResult)
                return
            }
            is MoveResult.Failure -> {
                outputView.printMoveResult(moveResult)
                processTurn(game)
            }
        }
    }

    private fun <T> retryOnError(function: () -> T): T {
        return runCatching { function() }.getOrElse { error ->
            println(error.message)
            retryOnError(function)
        }
    }
}
