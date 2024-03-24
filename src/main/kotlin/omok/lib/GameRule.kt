package omok.lib

import omok.model.board.Stone
import omok.model.omokGame.Board

interface GameRule {
    fun setupBoard(board: Board)

    fun isWinningMove(
        rowCoords: Int,
        columnCoords: Int,
        stone: Stone,
    ): Boolean

    fun findForbiddenPositions(stone: Stone): List<Pair<Int, Int>>

    fun isMoveAllowed(
        board: Array<Array<Stone>>,
        rowCoords: Int,
        columnCoords: Int,
        stone: Stone,
        forbiddenPositions: List<Pair<Int, Int>>,
    ): Boolean
}
