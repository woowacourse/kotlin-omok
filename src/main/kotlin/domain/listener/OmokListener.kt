package domain.listener

import domain.OmokBoard
import domain.State
import domain.Stone
import view.InputView
import view.OutputView

class OmokListener : Listener {
    override fun onStoneRequest(): Stone {
        return InputView().readPosition()
    }

    override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {
        OutputView().printOmokState(omokBoard, state, stone)
    }

    override fun onMoveFail() {
        OutputView().printDuplicate()
    }

    override fun onForbidden() {
        OutputView().printForbidden()
    }

    override fun onFinish(state: State) {
        OutputView().printWinner(state)
    }
}
