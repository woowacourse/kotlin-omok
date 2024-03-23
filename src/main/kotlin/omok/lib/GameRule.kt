package omok.lib

import omok.model.board.CoordsNumber
import omok.model.board.Position
import omok.model.board.Stone
import omok.model.omokGame.Board

interface GameRule {
    fun setupBoard(board: Board)

    fun isWinningMove(
        rowCoords: CoordsNumber,
        columnCoords: CoordsNumber,
        stone: Stone,
    ): Boolean

    fun findForbiddenPositions(stone: Stone): List<Position>

    fun isMoveAllowed(
        board: Board,
        rowCoords: CoordsNumber,
        columnCoords: CoordsNumber,
        stone: Stone,
        forbiddenPositions: List<Position>,
    ): Boolean
}
