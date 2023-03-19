package domain

import domain.listener.Listener

class OmokGame(
    val omokBoard: OmokBoard = OmokBoard(),
    private val listener: Listener
) {
    fun runGame() {
        while (true) {
            doTurn(State.BLACK)
            if (omokBoard.isVictory(State.BLACK)) return listener.onFinish(State.BLACK)

            doTurn(State.WHITE)
            if (omokBoard.isVictory(State.WHITE)) return listener.onFinish(State.WHITE)
        }
    }

    private fun doTurn(state: State) {
        val stone = listener.onStoneRequest()
        if (!omokBoard.isEmpty(stone)) {
            listener.onMoveFail()
            return doTurn(state)
        }

        if (state == State.BLACK && omokBoard.isForbidden(stone)) {
            listener.onForbidden()
            return doTurn(state)
        }

        omokBoard.move(stone, state)
        listener.onMove(omokBoard, state.nextState(), stone)
    }
}
