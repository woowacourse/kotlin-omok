package domain

import domain.listener.OmokListener

class OmokGame(
    private val omokBoard: OmokBoard = OmokBoard(),
    private val omokGameListener: OmokListener,
    private var _turn: State = State.BLACK
) {
    val turn
        get() = _turn
    private var isOver = false

    fun runGame(): State {
        while (true) {
            doTurn(State.BLACK)
            if (omokBoard.isVictory(State.BLACK)) return omokGameListener.onFinish(State.BLACK)

            doTurn(State.WHITE)
            if (omokBoard.isVictory(State.WHITE)) return omokGameListener.onFinish(State.WHITE)
        }
    }

    fun initBoard(state: State, stone: Stone) {
        omokBoard.move(stone, state)
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

    private fun isVictory(state: State): Boolean {
        if (omokBoard.isVictory(state)) {
            omokGameListener.onFinish(state)
            return true
        }
        return false
    }
}
