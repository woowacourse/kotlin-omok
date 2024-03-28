package omok.model

class GameManager : GameStateManager {
    private val board = Board()
    private var gameState: GameState = GameState.Running.BlackTurn(board)

    override fun play(
        onTurn: (GameState) -> Unit,
        onBoard: (Board) -> Unit,
        onCoordinate: () -> Coordinate,
    ) {
        while (isRunning()) {
            playTurn(onTurn, onBoard, onCoordinate)
        }
        playTurn(onTurn, onBoard, onCoordinate)
    }

    private fun isRunning() = gameState is GameState.Running

    private fun playTurn(
        onTurn: (GameState) -> Unit,
        onBoard: (Board) -> Unit,
        onCoordinate: () -> Coordinate,
    ) {
        var retry = true
        while (retry) {
            runCatching {
                gameState = gameState.updateState(onTurn, onBoard, onCoordinate)
            }.onSuccess {
                retry = false
            }.onFailure { throwable ->
                println(ERROR_MESSAGE.format(throwable.message))
            }
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "예외가 발생했습니다: %s \n다시 시도해주세요."
    }
}
