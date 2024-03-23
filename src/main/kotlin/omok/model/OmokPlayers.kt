package omok.model

class OmokPlayers(val blackStonePlayer: Player, val whiteStonePlayer: Player) {
    fun firstOrderPlayer() = blackStonePlayer

    fun next(recentPlayer: Player) = if (recentPlayer == blackStonePlayer) whiteStonePlayer else blackStonePlayer
}
