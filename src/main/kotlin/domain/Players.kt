package domain

class Players(
    private val blackPlayer: Player = Player.BlackPlayer(),
    private val whitePlayer: Player = Player.WhitePlayer()
) {
    val nextPlayer: Player
        get() {
            currentPlayer = when (currentPlayer) {
                is Player.BlackPlayer -> whitePlayer
                is Player.WhitePlayer -> blackPlayer
            }
            return currentPlayer
        }
    private var currentPlayer: Player = whitePlayer
}
