package domain

class Players(
    private val blackPlayer: Player = BlackPlayer(),
    private val whitePlayer: Player = WhitePlayer()
) {
    val nextPlayer: Player
        get() {
            currentPlayer = when (currentPlayer) {
                is BlackPlayer -> whitePlayer
                is WhitePlayer -> blackPlayer
            }
            return currentPlayer
        }
    private var currentPlayer: Player = whitePlayer
}
