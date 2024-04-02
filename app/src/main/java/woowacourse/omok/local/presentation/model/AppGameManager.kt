package woowacourse.omok.local.presentation.model

import omok.model.Board
import omok.model.Coordinate

class AppGameManager {
    val board = Board()
    var gameState: AppGameState = AppGameState.Running.BlackTurn(board)
        private set
    
    fun isRunning(): Boolean = gameState is AppGameState.Running
    
    fun isFinish(): Boolean = gameState is AppGameState.Finish
    
    fun playTurn(onCoordinate: () -> Coordinate) {
        if (isRunning()) {
            gameState = gameState.updateState(onCoordinate)
        }
    }
    
    fun restartGame() {
        gameState = AppGameState.Running.BlackTurn(board)
        board.clear()
    }
}
