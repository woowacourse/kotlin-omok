package woowacourse.omok.model.stonestate

import woowacourse.omok.model.GameState
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.stone.Stone

sealed class StoneState(private val stone: Stone) {
    abstract fun put(player: Player): GameState.LoadStoneState

    abstract fun rollback(): StoneState

    fun isSameState(otherStone: StoneState): Boolean {
        return otherStone::class == this::class
    }
}
