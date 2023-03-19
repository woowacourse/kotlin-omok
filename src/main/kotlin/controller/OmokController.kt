package controller

import domain.state.running.BlackTurn
import domain.state.end.End
import domain.state.State
import domain.state.running.WhiteTurn
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType
import view.InputView
import view.OutputView

class OmokController(
    val inputView: InputView = InputView(),
    val outputView: OutputView = OutputView(),
) {

    fun run() {
        val board: Board = Board()
        var state: State = BlackTurn(board)

        outputView.printOmokStart()
        while (state !is End) {
            outputView.printTurn(state, board.stones)

            when (state) {
                is BlackTurn -> state = state.put(Stone(inputView.inputStonePosition(), StoneType.BLACK))
                is WhiteTurn -> state = state.put(Stone(inputView.inputStonePosition(), StoneType.WHITE))
            }

            outputView.printBoard(board.stones)
        }

        outputView.printWinner(state)
    }
}
