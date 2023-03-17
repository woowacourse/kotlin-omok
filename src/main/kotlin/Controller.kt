import domain.Listener
import domain.OmokGame
import domain.State
import domain.Stone
import domain.board.Board
import view.InputView
import view.OutputView

class Controller {

    private val omokGameListener = object : Listener {
        override fun onStoneRequest(): Stone {
            return InputView.readPosition()
        }

        override fun onMove(board: Board, state: State, stone: Stone) {
            OutputView.printOmokState(board, state, stone)
        }

        override fun onMoveFail() {
            OutputView.printDuplicate()
        }

        override fun onForbidden() {
            OutputView.printForbidden()
        }

        override fun onFinish(state: State) {
            OutputView.printWinner(state)
        }
    }

    fun run() {
        val omokGame = OmokGame(omokGameListener = omokGameListener)
        OutputView.printStart()
        omokGame.runGame()
    }
}
