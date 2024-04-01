package woowacourse.omok.model.omokGame

import GameRuleAdapter
import woowacourse.omok.model.board.CoordsNumber
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone

class Board(private var _gameBoard: Array<Array<Stone>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Stone.EMPTY } }) {
    private var renjuGameRule: GameRuleAdapter = GameRuleAdapter()

    init {
        renjuGameRule.setupBoard(this)
    }

    val gameBoard: Array<Array<Stone>>
        get() = _gameBoard.copyOf()

    var omokGameState = OmokGameState.RUNNING

    fun isRunning() = omokGameState == OmokGameState.RUNNING
    fun isStop() = omokGameState == OmokGameState.STOP

    fun setStone(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
    ) {
        _gameBoard[y.number][x.number] = stone
    }


    fun isNotEmpty(
        row: CoordsNumber,
        column: CoordsNumber,
    ): Boolean {
        return gameBoard[column.number][row.number] != Stone.EMPTY
    }

    fun findForbiddenPositions(stone: Stone): List<Position> {
        if (stone == Stone.WHITE) return listOf()
        return renjuGameRule.findForbiddenPositions(stone)
    }

    fun isMoveForbidden(
        rowCoords: CoordsNumber,
        columnCoords: CoordsNumber,
        forbiddenPositions: List<Position>,
    ): Boolean {
        return Position(columnCoords, rowCoords) in forbiddenPositions
    }

    fun gameOver() {
        omokGameState = OmokGameState.STOP
    }

    fun updateGameBoard(newGameBoard: Array<Array<Stone>>) {
        for (i in newGameBoard.indices) {
            for (j in newGameBoard[i].indices) {
                gameBoard[i][j] = newGameBoard[i][j]
            }
        }
    }

    companion object {
        const val BOARD_SIZE = 15
    }
}
