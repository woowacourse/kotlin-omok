package woowacourse.omok.local.presentation.model

import omok.model.Board
import omok.model.Coordinate
import woowacourse.omok.local.db.StoneDao
import woowacourse.omok.local.model.StoneEntity

class AppGameManager(private val stoneDao: StoneDao) {
    val board = Board()
    var gameState: AppGameState = AppGameState.Running.BlackTurn(board)
        private set
    
    fun isRunning(): Boolean = gameState is AppGameState.Running
    
    fun isFinish(): Boolean = gameState is AppGameState.Finish
    
    fun playTurn(onCoordinate: () -> Coordinate) {
        if (isRunning()) {
            gameState = gameState.updateState(onCoordinate)
            saveCurrentStone()
        }
    }
    
    private fun saveCurrentStone() {
        board.lastCoordinate?.let { saveStoneToDatabase(it) }
    }
    
    private fun saveStoneToDatabase(coordinate: Coordinate) {
        val stoneEntity = StoneEntity(0L, coordinate.x, coordinate.y)
        stoneDao.save(stoneEntity)
    }
    
    fun restartGame() {
        gameState = AppGameState.Running.BlackTurn(board)
        board.clear()
        stoneDao.drop()
    }
}
