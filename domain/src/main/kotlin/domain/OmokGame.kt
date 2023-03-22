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

    fun successTurn(stone: Stone, state: State): Boolean {
        if (!omokBoard.isEmpty(stone)) {
            omokGameListener.onMoveFail()
            return false
            // return doAndroidTurn(stone, state)
        }

        if (state == State.BLACK && omokBoard.isForbidden(stone)) {
            omokGameListener.onForbidden()
            return false
            // return doAndroidTurn(stone, state)
        }

        omokBoard.move(stone, state)
        omokGameListener.onMove(omokBoard, state.nextState(), stone)
        return true
    }

    fun isVictory(state: State): Boolean {
        if (omokBoard.isVictory(state)) {
            omokGameListener.onFinish(state)
            return true
        }
        return false
    }
}
