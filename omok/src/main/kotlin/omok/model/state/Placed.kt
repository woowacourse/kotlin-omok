package omok.model.state

import omok.model.ErrorType.AlreadyExistStone
import omok.model.turn.Turn

abstract class Placed(private val stone: Stone) : StoneState {
    override fun put(turn: Turn): Result<StoneState> {
        return Result.failure(AlreadyExistStone())
    }

    override fun rollback(): StoneState {
        return Clear(stone)
    }

    companion object {
        const val BLACK_NUMBER = 1
        const val WHITE_NUMBER = 2
    }
}
