package omok.model

import omok.library.BlackStoneOmokRule

class Board(private val boardSize: Int = BOARD_SIZE) : BoardInterface {
    val layout: Array<Array<PositionType>> = Array(boardSize) { Array(boardSize) { PositionType.EMPTY } }
    var lastCoordinate: Coordinate? = null
        private set
    private val blackStoneOmokRule: BlackStoneOmokRule = BlackStoneOmokRule()

    override fun placeStone(
        coordinate: Coordinate,
        positionType: PositionType,
    ) {
        if (layout[coordinate.x][coordinate.y] == PositionType.EMPTY) {
            layout[coordinate.x][coordinate.y] = positionType
            lastCoordinate = coordinate
        } else {
            throw IllegalArgumentException(ERROR_INVALID_POSITION)
        }
    }

    override fun setupBoard(current: PositionType) {
        when (current) {
            PositionType.BLACK_STONE -> {
                setBlock(
                    blackStoneOmokRule::isThreeThree,
                    blackStoneOmokRule::isFourFour,
                    blackStoneOmokRule::isMoreThanFive,
                )
            }

            PositionType.WHITE_STONE -> {
                removeBlock()
            }

            else -> throw IllegalArgumentException(ERROR_POSITION_TYPE)
        }
    }

    private fun setBlock(
        isThreeThree: (Int, Int, Array<Array<PositionType>>) -> Boolean,
        isFourFour: (Int, Int, Array<Array<PositionType>>) -> Boolean,
        isMoreThanFive: (Int, Int, Array<Array<PositionType>>) -> Boolean,
    ) {
        val blockPositions: MutableList<Pair<Int, Int>> = mutableListOf()

        for (i in MIN_INDEX until boardSize) {
            for (j in MIN_INDEX until boardSize) {
                if (isBlockPosition(i, j, isThreeThree, isFourFour, isMoreThanFive)) {
                    blockPositions.add(Pair(i, j))
                }
            }
        }
        blockPositions.forEach {
            layout[it.first][it.second] = PositionType.BLOCK
        }
    }

    private fun isBlockPosition(
        i: Int,
        j: Int,
        isThreeThree: (Int, Int, Array<Array<PositionType>>) -> Boolean,
        isFourFour: (Int, Int, Array<Array<PositionType>>) -> Boolean,
        isMoreThanFive: (Int, Int, Array<Array<PositionType>>) -> Boolean,
    ) = (isThreeThree(i, j, layout) || isFourFour(i, j, layout) || isMoreThanFive(i, j, layout)) &&
        layout[i][j] == PositionType.EMPTY

    private fun removeBlock() {
        layout.forEach { row ->
            row.forEachIndexed { index, stoneType ->
                if (stoneType == PositionType.BLOCK) {
                    row[index] = PositionType.EMPTY
                }
            }
        }
    }

    companion object {
        private const val MIN_INDEX = 0
        private const val BOARD_SIZE = 15
        private const val ERROR_INVALID_POSITION = "돌을 놓을 수 없는 자리입니다."
        private const val ERROR_POSITION_TYPE = "올바른 PositionType이 아닙니다."
    }
}
