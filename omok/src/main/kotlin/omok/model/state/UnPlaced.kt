package omok.model.state

import omok.model.ErrorType.AlreadyExistStone
import omok.model.turn.BlackTurn
import omok.model.turn.FinishedTurn
import omok.model.turn.Turn
import omok.model.turn.WhiteTurn

abstract class UnPlaced(private val stone: Stone) : StoneState {
    override fun put(turn: Turn): Result<StoneState> {
        return when (turn) {
            is BlackTurn -> Result.success(Black(stone))
            is WhiteTurn -> Result.success(White(stone))
            is FinishedTurn -> Result.failure(AlreadyExistStone())
        }
    }

    override fun rollback(): StoneState {
        return Clear(stone)
    }

    companion object {
        const val CLEAR_NUMBER = 0
    }
}
