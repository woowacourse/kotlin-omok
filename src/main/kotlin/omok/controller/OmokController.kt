package omok.controller

import omok.model.Board
import omok.model.adapter.RenjuRuleAdapter
import omok.model.game.GameState
import omok.model.stone.Point
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val board = Board(renjuRuleAdapter = RenjuRuleAdapter())
        outputView.printOmokStart()
        outputView.printBoard(board)
        retryOnError { processTurn(board) }
    }

    private fun processTurn(board: Board) {
        val lastStone = board.stones.lastStone
        val inputPoint: Point = inputView.readTurn(lastStone)
        val stone = Stone(inputPoint, lastStone?.color ?: StoneColor.BLACK)
        board.place(stone)
        outputView.printBoard(board)

        when (val gameState = board.gameState(stone)) {
            GameState.PLAYING -> processTurn(board)
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
