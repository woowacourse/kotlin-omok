package domain

class Players(
    private val blackPlayer: Player = Player.BlackPlayer(),
    private val whitePlayer: Player = Player.WhitePlayer()
) {
    fun nextPlayer(player: Player): Player {
        return when (player) {
            is Player.BlackPlayer -> whitePlayer
            is Player.WhitePlayer -> blackPlayer
        }
    }
}
