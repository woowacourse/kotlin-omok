package woowacourse.omok

import woowacourse.omok.state.GameState

class GameManager(private val gamePlayHandler: GamePlayHandler) {
    private var gameState: GameState = GameState.Playing.Start(Board())

    fun playTurn(coordinate: Coordinate) {
        gameState = gameState.placeStone(coordinate)
        gamePlayHandler.onUpdate(gameState)
    }

    fun reset() {
        gameState = GameState.Playing.Start(Board())
        gamePlayHandler.onUpdate(gameState)
    }
}
