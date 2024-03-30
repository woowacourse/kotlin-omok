package woowacourse.omok.model.game

import woowacourse.omok.model.player.Player

class OmokPlayers(val blackStonePlayer: Player, val whiteStonePlayer: Player) {
    fun firstOrderPlayer() = blackStonePlayer

    fun next(recentPlayer: Player): Player {
        return if (recentPlayer == blackStonePlayer) whiteStonePlayer else blackStonePlayer
    }
}
