package omok.model

class GameManager {
    private var gameState: GameState = GameState.Running.BlackTurn(Board())

    private fun isRunning() = gameState is GameState.Running

    fun play(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ) {
        while (isRunning()) {
            runCatching {
                gameState = gameState.placeStone(onTurn, onRead, onShow)
            }.onFailure {
                println(it.message)
            }
        }
    }

    fun showGameStart(onStart: (Board) -> Unit) {
        onStart(gameState.board)
    }
}
