package woowacourse.omok.model.game

import woowacourse.omok.data.adapter.StonePosition
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.player.Player

class TurnHistory(
    private val omokPlayers: OmokPlayers,
    lastStonePosition: StonePosition? = null,
) {
    var recentPlayer: Player = omokPlayers.firstOrderPlayer()
        private set

    var recentPosition: Position? = null
        private set

    init {
        lastStonePosition?.run {
            recentPlayer = if (stone == Stone.BLACK) omokPlayers.whiteStonePlayer else omokPlayers.blackStonePlayer
            recentPosition = position
        }
    }

    fun update(position: Position) {
        recentPlayer = omokPlayers.next(recentPlayer)
        recentPosition = position
    }

    fun clear() {
        recentPlayer = omokPlayers.firstOrderPlayer()
        recentPosition = null
    }
}
