package domain

import domain.listener.OmokListener

class OmokGame(
    val omokBoard: OmokBoard = OmokBoard(),
    private val omokGameListener: OmokListener,
    private var _turn: State = State.BLACK
) {
    val turn
        get() = _turn

    fun successTurn(stone: Stone, state: State): Boolean {
        if (!omokBoard.isEmpty(stone)) {
            omokGameListener.onMoveFail()
            return false
        }

        if (state == State.BLACK && omokBoard.isForbidden(stone)) {
            omokGameListener.onForbidden()
            return false
        }

        omokBoard.move(stone, state)
        omokGameListener.onMove(omokBoard, state.nextState(), stone)
        return true
    }

    fun changeTurn() {
        _turn = _turn.nextState()
    }

    fun isVictory(state: State): Boolean {
        if (omokBoard.isVictory(state)) {
            omokGameListener.onFinish(state)
            return true
        }
        return false
    }
}
