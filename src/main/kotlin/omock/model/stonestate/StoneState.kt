package omock.model.stonestate

import omock.model.player.Player
import omock.model.stone.Stone

sealed class StoneState(private val stone: Stone) {
    abstract fun put(player: Player): StoneState

    abstract fun rollback(): StoneState

    fun isSameState(otherStone: StoneState): Boolean{
        return otherStone::class == this::class
    }
}
