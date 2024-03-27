package omock.model.state

import omock.model.turn.Turn

interface StoneState {
    fun put(turn: Turn): Result<StoneState>

    fun rollback(): StoneState

    fun getNumber(): Int
}
