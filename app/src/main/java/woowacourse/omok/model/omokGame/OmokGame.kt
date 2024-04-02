package woowacourse.omok.model.omokGame

import woowacourse.omok.database.GameDao
import woowacourse.omok.mapper.StoneTypeMapper
import woowacourse.omok.model.GameRuleAdapter
import woowacourse.omok.model.board.CoordsNumber
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.omokGame.Board.Companion.BOARD_SIZE

class OmokGame(private val listener: GameEventListener) {
    val board = Board()
    var currentStone = Stone.BLACK
    private val renjuGameRule =
        GameRuleAdapter().apply {
            setupBoard(board)
        }

    fun isGameOver(): Boolean = board.isStop()

    fun findForbiddenPositions(): List<Position> =
        renjuGameRule.findForbiddenPositions(currentStone)

    fun saveGame(gameDao: GameDao) {
        gameDao.saveGame(board.gameBoard)
        gameDao.saveCurrentStone(currentStone.value)
    }

    fun loadGame(gameDao: GameDao) {
        board.updateGameBoard(gameDao.loadGame())
        val loadedStoneType = gameDao.loadCurrentStone()
        StoneTypeMapper.toDomainModel(loadedStoneType).let {
            if (it != null) {
                currentStone = it
            }
        }
    }

    fun processPlayerMove(columnIndex: Int, rowIndex: Int): Boolean {
        if (!board.isRunning()) return false
        val position = Position(CoordsNumber(columnIndex), CoordsNumber(rowIndex))
        val forbiddenPositions = renjuGameRule.findForbiddenPositions(currentStone)
        if (board.isMoveForbidden(
                CoordsNumber(columnIndex),
                CoordsNumber(rowIndex),
                forbiddenPositions,
            )
        ) {
            return false
        }
        if (board.isNotEmpty(CoordsNumber(columnIndex), CoordsNumber(rowIndex))) {
            return false
        }
        placeStone(position, currentStone)
        if (board.isStop()) {
            return true
        }
        return true
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

    fun getStoneAt(rowIndex: Int, columnIndex: Int): Stone {
        return board.gameBoard[columnIndex][rowIndex]
    }

    private fun placeStone(
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
