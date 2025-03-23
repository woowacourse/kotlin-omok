package omok.controller

import omok.model.Board
import omok.model.game.Game
import omok.model.game.GameState
import omok.model.stone.Point
import omok.model.stone.Stone
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val board = Board()
        val game = Game(board)
        outputView.printOmokStart()
        outputView.printBoard(board)
        playInitialTurn(board, game)
        retryOnError { processTurn(board, game) }
    }

    private fun playInitialTurn(
        board: Board,
        game: Game,
    ) {
        val inputPoint: Point = inputView.readInitialTurn()
        val stone = Stone(inputPoint, game.lastStone.color.reverse())
        game.play(stone, outputView::printError)
        outputView.printBoard(board)
    }

    private tailrec fun processTurn(
        board: Board,
        game: Game,
    ) {
        val inputPoint: Point = inputView.readTurn(game.lastStone)
        val stone = Stone(inputPoint, game.lastStone.color.reverse())
        game.play(stone, outputView::printError)
        outputView.printBoard(board)

        when (val gameState = game.gameState(stone)) {
            GameState.PLAYING -> processTurn(board, game)
            GameState.BLACK_OMOK -> outputView.printWinner(gameState)
            GameState.WHITE_OMOK -> outputView.printWinner(gameState)
        }
    }

    private fun <T> retryOnError(function: () -> T): T =
        runCatching { function() }.getOrElse { error ->
            println(error.message)
            retryOnError(function)
        }
}
