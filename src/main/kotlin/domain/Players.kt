package domain

class Players(
    private val blackPlayer: Player = BlackPlayer(),
    private val whitePlayer: Player = WhitePlayer()
) {
    val currentPlayer: Player
        get() {
            nextPlayer = when (nextPlayer) {
                is BlackPlayer -> whitePlayer
                is WhitePlayer -> blackPlayer
            }
            return nextPlayer
        }
    private var nextPlayer: Player = whitePlayer
}
