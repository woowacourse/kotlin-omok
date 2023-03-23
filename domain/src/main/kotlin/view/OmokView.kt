package domain.view

import domain.domain.Board
import domain.domain.CoordinateState
import domain.domain.Position

object OmokView {
    fun printStart() {
        OutputView.printStart()
    }

    fun putPhase(board: Board, turn: CoordinateState): Position {
        OutputView.printBoard(board)
        OutputView.printTurn(turn)
        OutputView.printLastPosition(board.lastPosition)
        OutputView.printRequestPosition()
        return InputView.inputPosition()
    }

    fun printResult(board: Board, turn: CoordinateState) {
        OutputView.printBoard(board)
        OutputView.printWinner(turn)
    }

    fun printError() {
        OutputView.printError()
    }
}
