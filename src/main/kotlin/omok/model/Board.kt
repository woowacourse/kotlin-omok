package omok.model

class Board(private val boardSize: Int = BOARD_SIZE) {
    private var boardLayout = BoardLayout(boardSize)
    var lastCoordinate: Coordinate? = null
        private set

    fun getBoardLayout(): Array<Array<PositionType>> {
        return boardLayout.deepCopy()
    }

    fun placeStone(
        coordinate: Coordinate,
        positionType: PositionType,
    ) {
        if (boardLayout[coordinate.x, coordinate.y] == PositionType.EMPTY) {
            boardLayout[coordinate.x, coordinate.y] = positionType
            lastCoordinate = coordinate
        } else {
            throw IllegalArgumentException(ERROR_INVALID_POSITION)
        }
    }

    fun setBlock(
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
            boardLayout[it.first, it.second] = PositionType.BLOCK
        }
    }

    private fun isBlockPosition(
        i: Int,
        j: Int,
        isThreeThree: (Int, Int, Array<Array<PositionType>>) -> Boolean,
        isFourFour: (Int, Int, Array<Array<PositionType>>) -> Boolean,
        isMoreThanFive: (Int, Int, Array<Array<PositionType>>) -> Boolean,
    ) = (
        isThreeThree(i, j, boardLayout.deepCopy()) ||
            isFourFour(i, j, boardLayout.deepCopy()) ||
            isMoreThanFive(i, j, boardLayout.deepCopy())
    ) && boardLayout.isEmpty(i, j)

    fun removeBlock() {
        for (x in 0 until boardSize) {
            for (y in 0 until boardSize) {
                if (boardLayout[x, y] == PositionType.BLOCK) {
                    boardLayout[x, y] = PositionType.EMPTY
                }
            }
        }
    }

    companion object {
        private const val MIN_INDEX = 0
        private const val BOARD_SIZE = 15
        private const val ERROR_INVALID_POSITION = "돌을 놓을 수 없는 자리입니다."
    }
}
