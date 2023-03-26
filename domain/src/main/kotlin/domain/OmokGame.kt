package domain

import domain.listener.OmokListener

class OmokGame(
    val omokBoard: OmokBoard = OmokBoard(),
    private val omokGameListener: OmokListener,
    private var _turn: State = State.BLACK
) {
    val turn
        get() = _turn
    private var isOver = false

    fun successTurn2(stone: Stone, state: State): Boolean {
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

    fun successTurn(stone: Stone): Boolean {
        if (!isGameOver()) {
            when {
                !omokBoard.isEmpty(stone) -> omokGameListener.onMoveFail()
                _turn == State.BLACK && omokBoard.isForbidden(stone) -> omokGameListener.onForbidden()
                else -> null
            }?.let { return false }
            moveStone(stone)
            isOver = isVictory(_turn)
            return true
        }
        return false
    }

    fun goNext() {
        _turn = _turn.nextState()
    }

    fun isGameOver(): Boolean {
        return isOver
    }

    private fun moveStone(stone: Stone) {
        omokBoard.move(stone, _turn)
        omokGameListener.onMove(omokBoard, _turn, stone)
    }

    fun isVictory(state: State): Boolean {
        if (omokBoard.isVictory(state)) {
            omokGameListener.onFinish(state)
            return true
        }
        return false
    }
}
