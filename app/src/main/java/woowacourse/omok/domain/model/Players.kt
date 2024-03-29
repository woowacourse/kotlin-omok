package woowacourse.omok.domain.model

class Players(private val blackStonePlayer: Player, private val whiteStonePlayer: Player) {
    fun firstOrderedPlayer(): Player = blackStonePlayer

    fun nextOrder(player: Player): Player = if (player == blackStonePlayer) whiteStonePlayer else blackStonePlayer
}
