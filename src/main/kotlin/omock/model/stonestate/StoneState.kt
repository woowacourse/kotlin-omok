package omock.model.stonestate

import omock.model.player.Player

interface StoneState {
    fun put(player: Player): StoneState

    fun rollback(): StoneState
}
