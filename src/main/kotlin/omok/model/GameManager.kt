package omok.model

import omok.ExceptionMessageHandler

class GameManager(private val exceptionMessageHandler: ExceptionMessageHandler) {
    private var gameState: GameState = GameState.Running.BlackTurn(Board())

    fun play(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ) {
        while (isRunning()) {
            playTurn(onTurn, onRead, onShow)
        }
        showGameResult(onTurn, onShow)
    }

    private fun isRunning() = gameState is GameState.Running

    private fun playTurn(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ) = runCatching {
        gameState = gameState.placeStone(onTurn, onRead, onShow)
    }.onFailure { throwable ->
        exceptionMessageHandler.onMessageHandle(throwable)
    }

    private fun showGameResult(
        onTurn: (GameState) -> Unit,
        onShow: (Board) -> Unit,
    ) {
        onTurn(gameState)
        onShow(gameState.board)
    }
}
