package woowacourse.omok

import omok.library.BlackOmokRule
import omok.library.WhiteOmokRule

class Board(private val boardSize: Int = BOARD_SIZE) {
    private var boardLayout = BoardLayout(boardSize)
    var lastCoordinate: Coordinate? = null
        private set
    var currentType: PositionType = PositionType.BLACK_STONE
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
        this.currentType = currentType
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
        coordinate: Coordinate,
        positionType: PositionType,
    ): Boolean {
        return when (positionType) {
            PositionType.BLACK_STONE -> {
                BlackOmokRule.isWin(coordinate.x, coordinate.y, boardLayout.deepCopy())
            }
            PositionType.WHITE_STONE -> {
                WhiteOmokRule.isWin(coordinate.x, coordinate.y, boardLayout.deepCopy())
            }
            else -> false
        }
    }

    // 겹치게 놓을 수 없다, 이미 놓은 곳이다, 놓은 곳이다, 오목이다,
    fun placeStone(
        coordinate: Coordinate,
    ): PlaceResult {
        return when (boardLayout[coordinate.x, coordinate.y]) {
            PositionType.BLOCK -> {
                PlaceResult.Block
            }
            PositionType.EMPTY -> {
                boardLayout[coordinate.x, coordinate.y] = currentType
                lastCoordinate = coordinate
                if (isWin(coordinate, currentType)) PlaceResult.Omok else PlaceResult.Done
            }
            else -> {
                PlaceResult.Duplicate
            }
        }
    }

    companion object {
        const val BOARD_SIZE = 15
        private const val MIN_INDEX = 0
    }
}
