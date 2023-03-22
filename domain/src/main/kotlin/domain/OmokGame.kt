package domain

import domain.listener.OmokListener

class OmokGame(
    private val omokBoard: OmokBoard = OmokBoard(),
    private val omokGameListener: OmokListener,
) {
    var turn = State.BLACK
        private set
    private var isOver = false

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

    fun successTurn(stone: Stone): Boolean {
        if (!isOver) {
            when {
                !omokBoard.isEmpty(stone) -> omokGameListener.onMoveFail()
                turn == State.BLACK && omokBoard.isForbidden(stone) -> omokGameListener.onForbidden()
                else -> null
            }?.let { return false }
            moveStone(stone)
            isOver = isVictory(turn)
            turn = turn.nextState()
            return true
        }
        return false
    }

    private fun moveStone(stone: Stone) {
        omokBoard.move(stone, turn)
        omokGameListener.onMove(omokBoard, turn, stone)
    }

    private fun isVictory(state: State): Boolean {
        if (omokBoard.isVictory(state)) {
            omokGameListener.onFinish(state)
            return true
        }
        return false
    }
}
