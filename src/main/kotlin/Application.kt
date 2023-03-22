import domain.state.BlackTurn
import domain.state.End
import domain.state.State
import domain.state.WhiteTurn
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType
import view.InputView
import view.OutputView

fun main() {
    run()
}

fun run() {
    val board: Board = Board()
    var state: State = BlackTurn(board)

    OutputView.printOmokStart()
    startGame(board, state)
}

private fun startGame(board: Board, state: State) {
    OutputView.printTurn(state, board.stones)
    when (state) {
        is BlackTurn -> {
            val currentState = state.put(Stone(InputView.inputStonePosition(), StoneType.BLACK))
            OutputView.printBoard(board.stones)
            startGame(board, currentState)
        }

        is WhiteTurn -> {
            val currentState = state.put(Stone(InputView.inputStonePosition(), StoneType.WHITE))
            OutputView.printBoard(board.stones)
            startGame(board, currentState)
        }

        is End -> OutputView.printWinner(state)
    }
}
