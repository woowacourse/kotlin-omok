package view

import domain.Board
import domain.CoordinateState
import domain.Position

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
