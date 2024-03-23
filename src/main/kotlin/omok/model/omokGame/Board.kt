package omok.model.omokGame

import omok.lib.GameRule
import omok.lib.RenjuGameRule
import omok.model.board.CoordsNumber
import omok.model.board.Position
import omok.model.board.Stone

class Board(val gameBoard: Array<Array<Stone>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Stone.EMPTY } }) {
    private var gameRule: GameRule = RenjuGameRule().apply { setupBoard(this@Board) }

    private var omokGameState = OmokGameState.RUNNING

    fun isRunning() = omokGameState == OmokGameState.RUNNING

    fun setStone(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
    ) {
        gameBoard[y.number][x.number] = stone
        if (checkGameOver(x, y, stone)) {
            gameFinish()
        }
    }

    fun isNotEmpty(
        row: CoordsNumber,
        column: CoordsNumber,
    ): Boolean {
        return gameBoard[column.number][row.number] != Stone.EMPTY
    }

    fun findForbiddenPositions(stone: Stone): List<Position> {
        return gameRule.findForbiddenPositions(stone)
    }

    fun isMoveForbidden(
        rowCoords: CoordsNumber,
        columnCoords: CoordsNumber,
        forbiddenPositions: List<Position>,
    ): Boolean {
        return Position(rowCoords, columnCoords) in forbiddenPositions
    }

    private fun gameFinish() {
        omokGameState = OmokGameState.STOP
    }

    private fun checkGameOver(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
    ): Boolean {
        return gameRule.isWinningMove(x, y, stone)
    }

    companion object {
        const val BOARD_SIZE = 15
    }
}
