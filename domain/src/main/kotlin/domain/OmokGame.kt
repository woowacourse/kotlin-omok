package domain

import domain.listener.OmokListener

class OmokGame(
    val omokBoard: OmokBoard = OmokBoard(),
    private val omokGameListener: OmokListener,
    private var _turn: State = State.BLACK
) {
    val turn
        get() = _turn

    fun successTurn(stone: Stone): Boolean {
        if (!omokBoard.isEmpty(stone)) {
            omokGameListener.onMoveFail()
            return false
        }

        if (_turn == State.BLACK && omokBoard.isForbidden(stone)) {
            omokGameListener.onForbidden()
            return false
        }

        omokBoard.move(stone, _turn)
        omokGameListener.onMove(omokBoard, _turn.nextState(), stone)
        _turn = _turn.nextState()
        return true
    }

    fun isVictory(): Boolean {
        if (omokBoard.isVictory(_turn.prevState())) {
            omokGameListener.onFinish(_turn.prevState())
            return true
        }
        return false
    }
}
