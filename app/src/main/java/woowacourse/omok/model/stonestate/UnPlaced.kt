package woowacourse.omok.model.stonestate

import woowacourse.omok.model.GameState
import woowacourse.omok.model.player.BlackPlayer
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.player.WhitePlayer
import woowacourse.omok.model.stone.Stone

sealed class UnPlaced(private val stone: Stone) : StoneState(stone) {
    override fun put(player: Player): GameState.LoadStoneState {
        return when (player) {
            is BlackPlayer -> GameState.LoadStoneState.Success(Black(stone))
            is WhitePlayer -> GameState.LoadStoneState.Success(White(stone))
        }
    }

    override fun rollback(): StoneState {
        return Clear(stone)
    }
}
