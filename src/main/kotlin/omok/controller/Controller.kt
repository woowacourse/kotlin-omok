package omok.controller

import omok.GamePlayHandler
import omok.model.Board
import omok.GameManager
import omok.model.GameState
import omok.model.Position
import omok.view.BoardOutputView
import omok.view.PositionInputView
import omok.view.GameStateOutputView

class Controller : GamePlayHandler {
    private val gameManager: GameManager = GameManager(this)

    fun play() {
        printStart()
        gameManager.play()
    }

    override fun readPosition(): Position {
        return PositionInputView.readPosition()
    }

    override fun printBoard(board: Board) {
        BoardOutputView.printBoard(board)
    }

    override fun printRunningInfo(gameState: GameState) {
        GameStateOutputView.printRunningInfo(gameState)
    }

    private fun printStart() {
        GameStateOutputView.printStartHeader()
    }
}
