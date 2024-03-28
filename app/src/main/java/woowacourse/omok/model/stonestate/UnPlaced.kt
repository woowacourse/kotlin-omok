package woowacourse.omok.model.stonestate

import woowacourse.omok.model.player.BlackPlayer
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.player.WhitePlayer
import woowacourse.omok.model.stone.Stone

sealed class UnPlaced(private val stone: Stone) : StoneState(stone) {
    override fun put(player: Player): StoneState {
        return when (player) {
            is BlackPlayer -> Black(stone)
            is WhitePlayer -> White(stone)
        }
    }

    override fun rollback(): StoneState {
        return Clear(stone)
    }
}
