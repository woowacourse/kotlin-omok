package omok.model

import omok.library.RenjuRule

class OmokGame(
    private val rule: RenjuRule,
) {
    private val gameBoard: Array<Array<OmokStone>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Empty() } }
    private val omokStones = mutableListOf<OmokStone>()

    fun getBoard() = gameBoard

    fun getCurrentStone(): OmokStone? = omokStones.lastOrNull()

    fun getForbiddenPositions(): List<BoardPosition> =
        rule.findForbiddenMoves(gameBoard, getCurrentStone()).map {
            BoardPosition(BoardCoordinate(it.first), BoardCoordinate(it.second))
        }

    fun isRunning(): Boolean {
        return !(rule.isGameOver(gameBoard, getCurrentStone()))
    }

    fun placeStoneOnBoard(stone: OmokStone): Boolean {
        if (!canSetStone(stone)) return false
        gameBoard[stone.getRow()][stone.getColumn()] = stone
        omokStones.add(stone)
        return true
    }

    fun getNextStoneType(): OmokStoneType {
        val currentStone = getCurrentStone()
        return if (currentStone == null || currentStone.isWhite) OmokStoneType.BLACK else OmokStoneType.WHITE
    }

    fun generateNextOmokStone(boardPosition: BoardPosition): OmokStone {
        val currentStone = getCurrentStone()
        return if (currentStone == null || currentStone.isWhite) {
            Black(boardPosition)
        } else {
            White(boardPosition)
        }
    }

    private fun canSetStone(stone: OmokStone): Boolean {
        if (stone.boardPosition in omokStones.map { omokStone -> omokStone.boardPosition }) return false
        if (stone.boardPosition in getForbiddenPositions()) return false
        return true
    }

    companion object {
        const val BOARD_SIZE = 15
        const val MIN_COUNT_FOR_WIN = 5
        const val DIRECTION_HALF_COUNT = 4
    }
}
