package omock.model.state

import omock.model.turn.Turn

interface StoneState {
    fun put(player: Turn): StoneState

    fun rollback(): StoneState
}
