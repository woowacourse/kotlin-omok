package omok.model

class Players(private val blackStonePlayer: Player2, private val whiteStonePlayer: Player2) {
    fun firstOrderedPlayer(): Player2 = blackStonePlayer

    fun nextOrder(player: Player2): Player2 = if (player == blackStonePlayer) whiteStonePlayer else blackStonePlayer
}
