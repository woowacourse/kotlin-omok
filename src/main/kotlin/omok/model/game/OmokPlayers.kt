package omok.model.game

import omok.model.player.Player

class OmokPlayers(val blackStonePlayer: Player, val whiteStonePlayer: Player) {
    fun firstOrderPlayer() = blackStonePlayer

    fun next(recentPlayer: Player): Player {
        return if (recentPlayer == blackStonePlayer) whiteStonePlayer else blackStonePlayer
    }
}
