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
    private val gameBoard: Array<Array<OmokStone>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Empty() } }
    private val omokStones = mutableListOf<OmokStone>()
    fun saveTo(): String {
        val stringBuilder = StringBuilder()
        for (row in gameBoard.indices) {
            for (col in gameBoard[row].indices) {
                val stone = gameBoard[row][col]
                if (stone !is Empty) {
                    val stoneType = if (stone is Black) "B" else "W"
                    stringBuilder.append("$row,$col,$stoneType;")
                }
            }
        }
        return stringBuilder.toString()
    }

    fun restoreFrom(savedState: String) {
        // 초기 상태로 게임 보드를 리셋합니다.
        for (row in gameBoard.indices) {
            for (col in gameBoard[row].indices) {
                gameBoard[row][col] = Empty()
            }
        }
        omokStones.clear()

        val stoneInfos = savedState.split(';')
        for (info in stoneInfos) {
            val parts = info.split(',')
            if (parts.size == 3) {
                val row = parts[0].toInt()
                val col = parts[1].toInt()
                val type = parts[2]

                val position = BoardPosition(BoardCoordinate(row), BoardCoordinate(col))
                val stone = when (type) {
                    "B" -> Black(position)
                    "W" -> White(position)
                    else -> continue
                }
                gameBoard[row][col] = stone
                omokStones.add(stone)
            }
        }
    }

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
