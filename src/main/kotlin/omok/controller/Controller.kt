package omok.controller

import omok.ExceptionMessageHandler
import omok.model.Board
import omok.GameManager
import omok.model.GameState
import omok.model.Position
import omok.view.BoardOutputView
import omok.view.PositionInputView
import omok.view.GameStateOutputView

class Controller() : ExceptionMessageHandler {
    private val gameManager: GameManager = GameManager(this)

    fun play() {
        printStart()
        gameManager.play(::printRunningInfo, ::readPosition, ::printBoard)
    }

    private fun readPosition(): Position {
        return PositionInputView.readPosition()
    }

    private fun printStart() {
        GameStateOutputView.printStartHeader()
    }

    private fun printBoard(board: Board) {
        BoardOutputView.printBoard(board)
    }

    private fun printRunningInfo(gameState: GameState) {
        GameStateOutputView.printRunningInfo(gameState)
    }

    override fun onMessageHandle(error: Throwable) {
        GameStateOutputView.printErrorMessage(error)
    }
}
