package woowacourse.omok.model

import omok.library.RenjuRule
import omok.model.Black
import omok.model.BoardCoordinate
import omok.model.BoardPosition
import omok.model.Empty
import omok.model.OmokStone
import omok.model.OmokStoneType
import omok.model.White

class OmokGame(
    private val rule: RenjuRule,
) {
    private var gameBoard: Array<Array<OmokStone>> =
        Array(BOARD_SIZE) { Array(BOARD_SIZE) { Empty() } }
    private val omokGameState = OmokGameState(gameBoard)
    private val omokStones = mutableListOf<OmokStone>()
    private var forbiddenPositions = emptyList<BoardPosition>()
    fun getState() = omokGameState
    fun getStoneTypeAtPosition(row: Int, col: Int) = gameBoard[row][col].getOmokStoneType()
    fun restoreFrom(savedState: String): Boolean {
        val stoneInfos = savedState.split(DELIMITER_INFO).filter { it.isNotEmpty() }
        for (info in stoneInfos) {
            parseStoneInfo(info)?.let {
                gameBoard[it.getRow()][it.getColumn()] = it
                omokStones.add(it)
            } ?: return false
        }
        updateForbiddenPositions()
        return true
    }

    fun getBoard() = gameBoard

    fun resetGame() {
        gameBoard = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Empty() } }
        omokStones.clear()
    }

    fun getCurrentStone(): OmokStone? = omokStones.lastOrNull()

    fun updateForbiddenPositions() {
        forbiddenPositions = rule.findForbiddenMoves(gameBoard, getCurrentStone()).map {
            BoardPosition(BoardCoordinate(it.first), BoardCoordinate(it.second))
        }
    }

    fun isRunning(): Boolean {
        return !(rule.isGameOver(gameBoard, getCurrentStone()))
    }

    fun placeStoneOnBoard(stone: OmokStone): Boolean {
        if (!canSetStone(stone)) return false
        gameBoard[stone.getRow()][stone.getColumn()] = stone
        omokStones.add(stone)
        updateForbiddenPositions()
        return true
    }

    fun getNextStoneType(): OmokStoneType {
        val currentStone = getCurrentStone()
        return if (currentStone == null ||  currentStone.getOmokStoneType() == OmokStoneType.WHITE) OmokStoneType.BLACK else OmokStoneType.WHITE
    }

    fun generateNextOmokStone(boardPosition: BoardPosition): OmokStone {
        val currentStone = getCurrentStone()
        return if (currentStone == null || currentStone.getOmokStoneType() == OmokStoneType.WHITE) {
            Black(boardPosition)
        } else {
            White(boardPosition)
        }
    }

    private fun canSetStone(stone: OmokStone): Boolean {
        if (stone.boardPosition in omokStones.map { omokStone -> omokStone.boardPosition }) return false
        if (stone.boardPosition in forbiddenPositions) return false
        return true
    }

    private fun parseStoneInfo(info: String): OmokStone? {
        val parts = info.split(DELIMITER_PARTS)
        if (parts.size != 3) return null

        val row = parts[0].toIntOrNull() ?: return null
        val col = parts[1].toIntOrNull() ?: return null
        val type = parts[2]

        val position = BoardPosition(BoardCoordinate(row), BoardCoordinate(col))
        return when (type) {
            STONE_TYPE_BLACK -> Black(position)
            STONE_TYPE_WHITE -> White(position)
            else -> null
        }
    }

    companion object {
        const val BOARD_SIZE = 15
        const val MIN_COUNT_FOR_WIN = 5
        const val DIRECTION_HALF_COUNT = 4
        const val DELIMITER_INFO = ";"
        const val DELIMITER_PARTS = ","
        const val STONE_TYPE_BLACK = "B"
        const val STONE_TYPE_WHITE = "W"

    }
}
