package omok.model.state

import omok.model.turn.Turn

interface StoneState {
    fun put(turn: Turn): Result<StoneState>

    fun rollback(): StoneState

    fun getNumber(): Int
}
