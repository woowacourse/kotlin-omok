package omok.model

import omok.library.BlackOmokRule
import omok.library.WhiteOmokRule

class Board(private val boardSize: Int = BOARD_SIZE) {
    private var boardLayout = BoardLayout(boardSize)
    var lastPosition: Position? = null
        private set

    fun getBoardLayout(): Array<Array<PositionType>> {
        return boardLayout.deepCopy()
    }

    fun setUpBoard(currentType: PositionType) {
        when (currentType) {
            PositionType.BLACK_STONE -> {
                setBlock()
            }
            else -> {
                removeBlock()
            }
        }
    }

    private fun setBlock() {
        val blockPositions: MutableList<Pair<Int, Int>> = mutableListOf()

        for (i in MIN_INDEX until BOARD_SIZE) {
            for (j in MIN_INDEX until BOARD_SIZE) {
                if (isBlockPosition(i, j)) {
                    blockPositions.add(Pair(i, j))
                }
            }
        }
        blockPositions.forEach {
            boardLayout[it.first, it.second] = PositionType.BLOCK
        }
    }

    private fun isBlockPosition(
        i: Int,
        j: Int,
    ) = (
        BlackOmokRule.isThreeThree(i, j, boardLayout.deepCopy()) ||
            BlackOmokRule.isFourFour(i, j, boardLayout.deepCopy()) ||
            BlackOmokRule.isMoreThanFive(i, j, boardLayout.deepCopy())
    ) &&
        boardLayout[i, j] == PositionType.EMPTY

    private fun removeBlock() {
        for (x in 0 until boardSize) {
            for (y in 0 until boardSize) {
                if (boardLayout[x, y] == PositionType.BLOCK) {
                    boardLayout[x, y] = PositionType.EMPTY
                }
            }
        }
    }

    private fun isWin(
        position: Position,
        positionType: PositionType,
    ): Boolean {
        return when (positionType) {
            PositionType.BLACK_STONE -> {
                BlackOmokRule.isWin(position.coordinate.x, position.coordinate.y, boardLayout.deepCopy())
            }
            PositionType.WHITE_STONE -> {
                WhiteOmokRule.isWin(position.coordinate.x, position.coordinate.y, boardLayout.deepCopy())
            }
            else -> false
        }
    }

    // 겹치게 놓을 수 없다, 이미 놓은 곳이다, 놓은 곳이다, 오목이다,
    fun placeStone(
        position: Position,
        positionType: PositionType,
    ): BoardResult {
        return when (boardLayout[position.coordinate.x, position.coordinate.y]) {
            PositionType.BLOCK -> {
                BoardResult.Block
            }
            PositionType.EMPTY -> {
                boardLayout[position.coordinate.x, position.coordinate.y] = positionType
                lastPosition = position
                if (isWin(position, positionType)) BoardResult.Omok else BoardResult.Done
            }
            else -> {
                BoardResult.Duplicate
            }
        }
    }

    companion object {
        private const val MIN_INDEX = 0
        private const val BOARD_SIZE = 15
    }
}
