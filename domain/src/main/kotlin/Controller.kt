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
        var turn = State.BLACK
        while (true) {
            omokGame.nextTurn(turn)
            if (omokGame.isVictory(turn)) break
            turn = turn.nextState()
        }
    }

    private fun OmokGame.nextTurn(turn: State) {
        val stone = inputView.readPosition()
        if (!this.successTurn2(stone, turn)) return nextTurn(turn)
    }
}
