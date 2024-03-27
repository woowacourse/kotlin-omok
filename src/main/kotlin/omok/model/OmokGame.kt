package omok.model

import omok.library.RenjuRule

class OmokGame(
    val gameBoard: Array<Array<OmokStone>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Empty() } },
    private val rule: RenjuRule,
) {
    val omokStones = mutableListOf<OmokStone>()
    private var omokGameState = OmokGameState.RUNNING

    fun getCurrentStone(): OmokStone? = omokStones.lastOrNull()

    fun getForbiddenPositions() = rule.findForbiddenMovesForStone(gameBoard, getCurrentStone())

    fun isRunning() = omokGameState == OmokGameState.RUNNING

    fun setStone(omokStone: OmokStone): Boolean {
        if (canSetStone(omokStone)) {
            gameBoard[omokStone.columnCoords.number][omokStone.rowCoords.number] = omokStone
            omokStones.add(omokStone)
            updateGameStatus()
            return true
        }
        return false
    }

    private fun updateGameStatus() {
        val currentStone = getCurrentStone()

        if (currentStone != null &&
            rule.isGameOver(
                gameBoard,
                currentStone.rowCoords,
                currentStone.columnCoords,
                currentStone,
            )
        ) {
            finish()
        }
    }

    private fun canSetStone(omokStone: OmokStone): Boolean {
        if (omokStone in omokStones) return false
        if (omokStone.columnCoords to omokStone.rowCoords in getForbiddenPositions()) return false
        return true
    }

    private fun finish() {
        omokGameState = OmokGameState.STOP
    }

    companion object {
        const val BOARD_SIZE = 15
        const val MIN_COUNT_FOR_WIN = 5
        const val DIRECTION_HALF_COUNT = 4
    }
}
