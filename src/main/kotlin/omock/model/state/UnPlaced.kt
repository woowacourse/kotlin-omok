package omock.model.state

import omock.model.ErrorType.AlreadyExistStone
import omock.model.turn.BlackTurn
import omock.model.turn.FinishedTurn
import omock.model.turn.Turn
import omock.model.turn.WhiteTurn

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
