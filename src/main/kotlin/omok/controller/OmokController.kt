package omok.controller

import omok.model.Board
import omok.model.Game
import omok.model.GameState
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
        retryOnError { processTurn(game) }
    }

    private tailrec fun processTurn(game: Game) {
        val (x: Int, y: Int) = inputView.readTurn(game)
        val position = Position(x, y)
        val gameState: GameState = game.processTurn(position, game.chooseTurn())
        outputView.printBoard(game.board)

        when (gameState) {
            GameState.PLAYING -> processTurn(game)
            GameState.FINISHED -> outputView.printWinner(game)
        }
    }

    private fun <T> retryOnError(function: () -> T): T {
        return runCatching { function() }.getOrElse { error ->
            println(error.message)
            retryOnError(function)
        }
    }
}
