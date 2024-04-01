package woowacourse.omok.model.omokGame

import GameRuleAdapter
import woowacourse.omok.database.GameDao
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.omokGame.Board.Companion.BOARD_SIZE

class OmokGame(private val listener: GameEventListener) {
    val board = Board()
    var currentStone = Stone.BLACK
    val renjuGameRule =
        GameRuleAdapter().apply {
            setupBoard(board)
        }

    fun loadGame(gameDao: GameDao) {
        val loadedGameBoard = gameDao.loadGame()
        this.board.gameBoard = loadedGameBoard
        val loadedStoneType = gameDao.loadCurrentStone()
        if (loadedStoneType != -1) {
            this.currentStone = Stone.entries.toTypedArray()[loadedStoneType]
        }
    }

    fun resetGame() {
        for (i in 0 until BOARD_SIZE) {
            for (j in 0 until BOARD_SIZE) {
                board.gameBoard[i][j] = Stone.EMPTY
                renjuGameRule.setupBoard(board)
            }
        }
        currentStone = Stone.BLACK
        board.omokGameState = OmokGameState.RUNNING
    }

    fun startGame(currentStone: Stone): Stone {
        listener.onGameStart()
        return currentStone
    }

    fun endGame(winner: Stone) {
        listener.onGameEnd(winner)
    }

    fun placeStone(
        position: Position,
        currentStone: Stone,
    ) {
        renjuGameRule.placeForRuleCheck(position.row, position.column, currentStone)
        board.setStone(position.row, position.column, currentStone)
        if (renjuGameRule.isWinningMove(position.row, position.column, currentStone)) {
            board.gameOver()
        }
        onStonePlaced(currentStone)
    }

    private fun onStonePlaced(currentStone: Stone) {
        val changeStone = togglePlayer(currentStone)
        listener.printBoard(
            board.gameBoard,
            renjuGameRule.findForbiddenPositions(
                changeStone,
            ),
        )
        this.currentStone = changeStone
    }

    private fun togglePlayer(currentStone: Stone): Stone =
        if (currentStone == Stone.BLACK) Stone.WHITE else Stone.BLACK
}
