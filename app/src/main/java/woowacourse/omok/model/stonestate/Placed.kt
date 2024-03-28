package woowacourse.omok.model.stonestate

import woowacourse.omok.model.player.Player
import woowacourse.omok.model.stone.Stone

sealed class Placed(private val stone: Stone) : StoneState(stone) {
    override fun put(player: Player): StoneState {
        throw IllegalArgumentException(ERROR_STONE_DUPLICATION)
    }

    override fun rollback(): StoneState {
        return Clear(stone)
    }

    companion object {
        private const val ERROR_STONE_DUPLICATION = "이미 돌이 있습니다."
    }
}
