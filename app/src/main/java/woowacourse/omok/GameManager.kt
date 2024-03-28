package woowacourse.omok

class GameManager(private val gamePlayHandler: GamePlayHandler) {
    private var gameState: GameState = GameState.Running.BlackTurn.Start(Board())

    fun playTurn(coordinate: Coordinate) {
        gameState = gameState.placeStone(coordinate)
        gamePlayHandler.onDraw(gameState)
    }
}
