package omok

import omok.model.Board
import omok.model.GameState

class GameManager(private val gamePlayHandler: GamePlayHandler) {
    private var gameState: GameState = GameState.Running.BlackTurn.Start(Board())

    fun play() {
        while (isRunning()) {
            playTurn()
        }
        showGameResult()
    }

    private fun isRunning() = gameState is GameState.Running

    private fun playTurn() {
        gamePlayHandler.printBoard(gameState.board)
        gamePlayHandler.printRunningInfo(gameState)

        val position = gamePlayHandler.readPosition()
        gameState = gameState.placeStone(position)
    }

    private fun showGameResult() {
        gamePlayHandler.printRunningInfo(gameState)
        gamePlayHandler.printBoard(gameState.board)
    }
}
