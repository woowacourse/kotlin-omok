package omok.model

class Board {
    val layout: Array<Array<PositionType>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { PositionType.EMPTY } }
    var lastPosition: Position? = null
    private lateinit var omokRule: OmokRule

    fun setupOmokRule(currentType: PositionType) {
        when (currentType) {
            PositionType.BLACK_STONE -> {
                omokRule = OmokRule(layout, PositionType.BLACK_STONE, PositionType.WHITE_STONE, BOARD_SIZE)
                setBlock(omokRule::checkThreeThree, omokRule::checkFourFour, omokRule::checkMoreThanFive)
            }

            PositionType.WHITE_STONE -> {
                omokRule = OmokRule(layout, PositionType.WHITE_STONE, PositionType.BLACK_STONE, BOARD_SIZE)
                removeBlock()
            }

            else -> throw IllegalArgumentException(ERROR_POSITION_TYPE)
        }
    }

    private fun setBlock(
        checkThreeThree: (Int, Int) -> Boolean,
        checkFourFour: (Int, Int) -> Boolean,
        checkMoreThanFive: (Int, Int) -> Boolean,
    ) {
        val blockPositions: MutableList<Pair<Int, Int>> = mutableListOf()

        for (i in MIN_INDEX until BOARD_SIZE) {
            for (j in MIN_INDEX until BOARD_SIZE) {
                if (isBlockPosition(i, j, checkThreeThree, checkFourFour, checkMoreThanFive)) {
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
        checkThreeThree: (Int, Int) -> Boolean,
        checkFourFour: (Int, Int) -> Boolean,
        checkMoreThanFive: (Int, Int) -> Boolean,
    ) = (checkThreeThree(i, j) || checkFourFour(i, j) || checkMoreThanFive(i, j)) && layout[i][j] == PositionType.EMPTY


    private fun removeBlock() {
        layout.forEach { row ->
            row.forEachIndexed { index, stoneType ->
                if (stoneType == PositionType.BLOCK) {
                    row[index] = PositionType.EMPTY
                }
            }
        }
    }

    fun isOmok(
        position: Position,
        positionType: PositionType,
    ): Boolean {
        return when (positionType) {
            PositionType.BLACK_STONE -> {
                omokRule.checkOmok(position.coordinate.x, position.coordinate.y) &&
                    !omokRule.checkMoreThanFive(position.coordinate.x, position.coordinate.y)
            }

            PositionType.WHITE_STONE -> {
                omokRule.checkOmok(position.coordinate.x, position.coordinate.y)
            }

            else -> throw IllegalArgumentException()
        }
    }

    fun placeStone(
        position: Position,
        positionType: PositionType,
    ) {
        if (layout[position.coordinate.x][position.coordinate.y] == PositionType.EMPTY) {
            layout[position.coordinate.x][position.coordinate.y] = positionType
            lastPosition = position
        } else {
            throw IllegalArgumentException(ERROR_INVALID_POSITION)
        }
    }

    companion object {
        private const val MIN_INDEX = 0
        private const val BOARD_SIZE = 15
        private const val ERROR_INVALID_POSITION = "돌을 놓을 수 없는 자리입니다."
        private const val ERROR_BLOCK_STONE = "BLOCK 생성을 실패했습니다."
        private const val ERROR_POSITION_TYPE = "올바른 PositionType이 아닙니다."
    }
}
