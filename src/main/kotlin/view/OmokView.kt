package domain.view

import domain.CoordinateState
import domain.CoordinateState.BLACK
import domain.Position
import domain.domain.state.BlackTurn
import domain.domain.state.State
import domain.domain.state.WhiteTurn
import domain.domain.state.WhiteWin
import view.InputView
import view.OutputView

class OmokView : Observer {
    private var preState: CoordinateState = BLACK

    fun printStart(state: State) {
        OutputView.printStart()
        drawBoard(state)
    }

    fun getInputPosition(): Position {
        return InputView.inputPosition()
    }

    private fun retryInput() {
        OutputView.printError()
        OutputView.printRequestPosition()
    }

    private fun drawBoard(state: State) {
        preState = state.getTurn()
        with(OutputView) {
            printBoard(state.stones)
            printTurn(state.getTurn())
            printLastPosition(state.stones.lastPosition)
            printRequestPosition()
        }
    }

    private fun printResult(state: State) {
        OutputView.printBoard(state.stones)
        OutputView.printWinner(state.getTurn())
    }

    override fun update(state: State) {
        when (state) {
            is BlackTurn -> updateView(state)
            is WhiteTurn -> updateView(state)
            is WhiteWin -> printResult(state)
        }
    }

    private fun updateView(state: State) {
        when (preState == state.getTurn()) {
            true -> retryInput()
            false -> drawBoard(state)
        }
    }
}
