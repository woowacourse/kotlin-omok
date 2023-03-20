package domain

import domain.listener.OmokListener

class OmokGame(
    private val omokBoard: OmokBoard = OmokBoard(),
    private val omokGameListener: OmokListener
) {
    fun runGame(): State {
        while (true) {
            doTurn(State.BLACK)
            if (omokBoard.isVictory(State.BLACK)) return omokGameListener.onFinish(State.BLACK)

            doTurn(State.WHITE)
            if (omokBoard.isVictory(State.WHITE)) return omokGameListener.onFinish(State.WHITE)
        }
    }

    private fun doTurn(state: State) {
        val stone = omokGameListener.onStoneRequest()
        if (!omokBoard.isEmpty(stone)) {
            omokGameListener.onMoveFail()
            return doTurn(state)
        }

        if (state == State.BLACK && omokBoard.isForbidden(stone)) {
            omokGameListener.onForbidden()
            return doTurn(state)
        }

        omokBoard.move(stone, state)
        omokGameListener.onMove(omokBoard, state.nextState(), stone)
    }
}
