package omok.controller

import omok.model.Board
import omok.model.Coordinate
import omok.model.GameManager
import omok.model.GameState
import omok.view.BoardOutputView
import omok.view.CoordinateInputView
import omok.view.GameStateOutputView

class Controller(private val gameManager: GameManager) {
    fun play() {
        printStart()
        gameManager.play(::printRunningInfo, ::printBoard, ::readPosition)
    }

    private fun readPosition(): Coordinate {
        return CoordinateInputView.readCoordinate()
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
}
