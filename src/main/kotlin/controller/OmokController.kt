package controller

import domain.state.BlackTurn
import domain.state.End
import domain.state.State
import domain.state.WhiteTurn
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType
import view.InputView
import view.OutputView

class OmokController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView(),
) {

    fun run() {
        val board: Board = Board()
        var state: State = BlackTurn(board)

        outputView.printOmokStart()
        startGame(board, state)
    }

    fun startGame(board: Board, state: State) {
        outputView.printTurn(state, board.stones)
        when (state) {
            is BlackTurn -> {
                val state = state.put(Stone(inputView.inputStonePosition(), StoneType.BLACK))
                outputView.printBoard(board.stones)
                startGame(board, state)
            }

            is WhiteTurn -> {
                val state = state.put(Stone(inputView.inputStonePosition(), StoneType.WHITE))
                outputView.printBoard(board.stones)
                startGame(board, state)
            }

            is End -> outputView.printWinner(state)
        }
    }
}
