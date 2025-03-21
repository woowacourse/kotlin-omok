package omok.controller

import omok.model.BlackPlayer
import omok.model.Game
import omok.model.GameState
import omok.model.Point
import omok.model.WhitePlayer
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        outputView.printOmokStart()
        val game = Game(BlackPlayer(), WhitePlayer())
        outputView.printBoard(game)
        val point: Point = inputView.readInitialTurn()
        game.play(point)
        outputView.printBoard(game)
        retryOnError { processTurn(game) }
    }

    private tailrec fun processTurn(game: Game) {
        val point: Point = inputView.readTurn(game.lastStone)
        val gameState: GameState = game.play(point)
        outputView.printBoard(game)

        when (gameState) {
            GameState.PLAYING -> processTurn(game)
            GameState.BLACK_OMOK -> outputView.printWinner(gameState)
            GameState.WHITE_OMOK -> outputView.printWinner(gameState)
        }
    }

    private fun <T> retryOnError(function: () -> T): T {
        return runCatching { function() }.getOrElse { error ->
            println(error.message)
            retryOnError(function)
        }
    }
}
