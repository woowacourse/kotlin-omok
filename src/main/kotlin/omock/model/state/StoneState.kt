package omock.model.state

import omock.model.turn.Turn

interface StoneState {
    fun put(turn: Turn): StoneState

    fun rollback(): StoneState
}
