package omok.domain

import omok.domain.gameState.BlackTurn
import omok.domain.gameState.GameState
import omok.domain.state.StoneState

private typealias Point = (StoneState, OmokPoint?) -> OmokPoint
private typealias BoardStatus = (OmokBoard) -> Unit

class OmokGame {
    fun play(getPoint: Point, output: BoardStatus) {
        var gameState: GameState = BlackTurn(OmokBoard(BOARD_X_SIZE, BOARD_Y_SIZE))
        var point: OmokPoint? = null
        while (gameState.isRunning) {
            point = getPoint(gameState.stoneState, point)
            runCatching { gameState = gameState.play(point) }.onFailure { println(it.message) }
            output(gameState.omokBoard)
        }
    }

    companion object {
        const val BOARD_X_SIZE = 15
        const val BOARD_Y_SIZE = 15
    }
}
