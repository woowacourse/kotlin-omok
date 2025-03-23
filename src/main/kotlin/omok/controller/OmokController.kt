package omok.controller

import omok.model.Board
import omok.model.Game
import omok.model.MoveResult
import omok.model.Position
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        outputView.printOmokStart()
        val game = Game(Board())
        outputView.printBoard(game.board)
        processTurn(game)
    }

    private tailrec fun processTurn(game: Game) {
        val (x: Int, y: Int) = retryOnError { inputView.readTurn(game) }
        val position = Position(x, y)
        val moveResult: MoveResult = game.processTurn(position, game.chooseTurn())
        outputView.printBoard(game.board)

        when (moveResult) {
            is MoveResult.Success.Playing -> processTurn(game)
            is MoveResult.Success.WhiteWin -> {
                outputView.printMoveResult(moveResult)
                return
            }
            is MoveResult.Success.BlackWin -> {
                outputView.printMoveResult(moveResult)
                return
            }
            is MoveResult.Fail -> {
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
