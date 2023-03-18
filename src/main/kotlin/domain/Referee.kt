package domain

class Referee(
    private val omokRule: OmokRule = OmokRuleAdapter()
) {

    fun isWin(omokBoard: OmokBoard, state: State): Boolean {
        val size = OmokBoard.BOARD_SIZE
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (omokBoard.value[i][j] == State.EMPTY) continue

                if (j + 4 < size && isHorizontalOmok(i, j, omokBoard, state)) return true
                if (i + 4 < size && isVerticalOmok(i, j, omokBoard, state)) return true
                if (i + 4 < size && j + 4 < size && isRightDiagonalOmok(i, j, omokBoard, state)) return true
                if (i + 4 < size && j - 4 >= 0 && isLeftDiagonalOmok(i, j, omokBoard, state)) return true
            }
        }
        return false
    }

    private fun isHorizontalOmok(i: Int, j: Int, omokBoard: OmokBoard, state: State): Boolean {
        if (
            omokBoard.value[i][j] == state &&
            omokBoard.value[i][j + 1] == state &&
            omokBoard.value[i][j + 2] == state &&
            omokBoard.value[i][j + 3] == state &&
            omokBoard.value[i][j + 4] == state
        ) return true
        return false
    }

    private fun isVerticalOmok(i: Int, j: Int, omokBoard: OmokBoard, state: State): Boolean {
        if (
            omokBoard.value[i][j] == state &&
            omokBoard.value[i + 1][j] == state &&
            omokBoard.value[i + 2][j] == state &&
            omokBoard.value[i + 3][j] == state &&
            omokBoard.value[i + 4][j] == state
        ) return true
        return false
    }

    private fun isRightDiagonalOmok(i: Int, j: Int, omokBoard: OmokBoard, state: State): Boolean {
        if (
            omokBoard.value[i][j] == state &&
            omokBoard.value[i + 1][j + 1] == state &&
            omokBoard.value[i + 2][j + 2] == state &&
            omokBoard.value[i + 3][j + 3] == state &&
            omokBoard.value[i + 4][j + 4] == state
        ) return true
        return false
    }

    private fun isLeftDiagonalOmok(i: Int, j: Int, omokBoard: OmokBoard, state: State): Boolean {
        if (
            omokBoard.value[i][j] == state &&
            omokBoard.value[i + 1][j - 1] == state &&
            omokBoard.value[i + 2][j - 2] == state &&
            omokBoard.value[i + 3][j - 3] == state &&
            omokBoard.value[i + 4][j - 4] == state
        ) return true
        return false
    }

    fun isMovable(myOmokBoard: OmokBoard, stone: Stone): Boolean {
        return omokRule.isMovable(myOmokBoard, stone)
    }
}
