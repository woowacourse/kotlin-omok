package domain

import domain.listener.OmokListener
import domain.turn.MoveResult

class OmokGame(
    val omokBoard: OmokBoard = OmokBoard(),
    private val omokGameListener: OmokListener,
    private var _turn: State = State.BLACK
) {
    val turn
        get() = _turn

    fun successTurn(stone: Stone, state: State): MoveResult {
        if (!omokBoard.isEmpty(stone)) {
            omokGameListener.onMoveFail()
            return MoveResult.Fail()
        }

        if (state == State.BLACK && omokBoard.isForbidden(stone)) {
            omokGameListener.onForbidden()
            return MoveResult.Fail()
        }

        omokBoard.move(stone, state)
        omokGameListener.onMove(omokBoard, state.nextState(), stone)
        return MoveResult.Success()
    }

    fun changeTurn(moveResult: MoveResult) {
        when (moveResult) {
            is MoveResult.Success -> _turn = _turn.nextState()
            is MoveResult.Fail -> _turn
        }
    }

    fun isVictory(state: State): Boolean {
        if (omokBoard.isVictory(state)) {
            omokGameListener.onFinish(state)
            return true
        }
        return false
    }
}
