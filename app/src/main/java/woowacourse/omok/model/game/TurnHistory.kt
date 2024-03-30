package woowacourse.omok.model.game

import woowacourse.omok.model.board.Position

class TurnHistory(private val omokPlayers: OmokPlayers) {
    var recentPlayer = omokPlayers.firstOrderPlayer()
        private set

    var recentPosition: Position? = null
        private set

    fun update(position: Position) {
        recentPlayer = omokPlayers.next(recentPlayer)
        recentPosition = position
    }

    fun clear() {
        recentPlayer = omokPlayers.firstOrderPlayer()
        recentPosition = null
    }
}
