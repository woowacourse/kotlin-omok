package omok.controller

import omok.ExceptionMessageHandler
import omok.model.Board
import omok.GameManager
import omok.model.GameState
import omok.model.Position
import omok.view.BoardView
import omok.view.InputView
import omok.view.OutputView

class Controller() : ExceptionMessageHandler {
    private val gameManager: GameManager = GameManager(this)

    fun play() {
        printStart()
        gameManager.play(::printRunningInfo, ::readPosition, ::printBoard)
    }

    private fun readPosition(): Position {
        return InputView.readPosition()
    }

    private fun printStart() {
        OutputView.printStartHeader()
    }

    private fun printBoard(board: Board) {
        BoardView.printBoard(board)
    }

    private fun printRunningInfo(gameState: GameState) {
        OutputView.printRunningInfo(gameState)
    }

    override fun onMessageHandle(error: Throwable) {
        OutputView.printErrorMessage(error)
    }
}
