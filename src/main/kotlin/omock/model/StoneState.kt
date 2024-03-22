package omock.model

import omock.model.turn.Turn

interface StoneState {
    fun put(player: Turn): StoneState

    fun rollback(): StoneState
}
