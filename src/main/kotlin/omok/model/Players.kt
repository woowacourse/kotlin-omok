package omok.model

class Players(private val blackStonePlayer: Player, private val whiteStonePlayer: Player) {
    fun firstOrderedPlayer(): Player = blackStonePlayer

    fun nextOrder(player: Player): Player = if (player == blackStonePlayer) whiteStonePlayer else blackStonePlayer
}

class Players2(private val blackStonePlayer: Player2, private val whiteStonePlayer: Player2) {
    fun firstOrderedPlayer(): Player2 = blackStonePlayer

    fun nextOrder(player: Player2): Player2 = if (player == blackStonePlayer) whiteStonePlayer else blackStonePlayer
}
