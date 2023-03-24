package controller

import domain.state.State
import domain.state.end.End
import domain.state.running.BlackTurn
import domain.stone.Board
import view.InputView
import view.OutputView

class OmokController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView(),
) {

    fun run() {
        val board: Board = Board()
        var state: State = BlackTurn()

        outputView.printOmokStart()
        while (state !is End) {
            outputView.printTurn(state, board.stones)
            state = state.next(board, inputView.inputStonePosition())
            outputView.printBoard(board.stones)
        }

        outputView.printWinner(state)
    }
}
