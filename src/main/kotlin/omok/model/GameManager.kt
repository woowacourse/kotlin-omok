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
            playTurn(onTurn, onRead, onShow)
        }
        gameResult(onTurn, onShow)
    }

    private fun gameResult(
        onTurn: (GameState) -> Unit,
        onShow: (Board) -> Unit,
    ) {
        onTurn(gameState)
        onShow(gameState.board)
    }

    private fun playTurn(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ) = runCatching {
        gameState = gameState.placeStone(onTurn, onRead, onShow)
    }.onFailure { throwable ->
        println(throwable.message)
    }
}
