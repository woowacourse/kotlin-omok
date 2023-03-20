import domain.OmokBoard
import domain.OmokGame
import domain.State
import domain.Stone
import domain.listener.OmokListener
import view.InputViewInterface
import view.OutputViewInterface

class Controller(
    val inputView: InputViewInterface,
    val outputView: OutputViewInterface,
) {
    private val omokGameListener = object : OmokListener {
        override fun onStoneRequest(): Stone {
            return inputView.readPosition()
        }

        override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {
            outputView.printOmokState(omokBoard, state, stone)
        }

        override fun onMoveFail() {
            outputView.printDuplicate()
        }

        override fun onForbidden() {
            outputView.printForbidden()
        }

        override fun onFinish(state: State): State {
            outputView.printWinner(state)
            return state
        }
    }

    fun run() {
        val omokGame = OmokGame(omokGameListener = omokGameListener)
        outputView.printStart()
        omokGame.runGame()
    }
}
