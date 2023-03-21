package domain

class Players(
    private val blackPlayer: Player = Player.BlackPlayer(),
    private val whitePlayer: Player = Player.WhitePlayer()
) {
    private var currentPlayer: Player = whitePlayer

    fun nextPlayer(): Player {
        currentPlayer = when (currentPlayer) {
            is Player.BlackPlayer -> whitePlayer
            is Player.WhitePlayer -> blackPlayer
        }
        return currentPlayer
    }
}
