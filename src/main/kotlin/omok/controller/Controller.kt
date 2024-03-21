package omok.controller

import omok.model.Board
import omok.model.GameManager
import omok.model.GameState
import omok.model.Position
import omok.view.BoardView
import omok.view.InputView
import omok.view.OutputView

class Controller(private val gameManager: GameManager) {
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
}
