package omok.controller

import omok.model.Board
import omok.model.GameState
import omok.model.Position
import omok.view.BoardView
import omok.view.InputView
import omok.view.OutputView

class Controller(private val board: Board = Board()) {
    fun play() {
        printStart()
        board.play(::printGameState, ::readPosition, ::printBoard)
    }

    private fun readPosition(): Position {
        return InputView.readPosition()
    }

    private fun printStart() {
        OutputView.printStartHeader()
        printBoard(board)
    }

    private fun printBoard(board: Board) {
        BoardView.printBoard(board)
    }

    private fun printGameState(gameState: GameState) {
        OutputView.printTurnInfo(gameState)
    }
}
