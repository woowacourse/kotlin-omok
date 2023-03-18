package domain.position

import domain.stone.Stone
import domain.stone.Stones

data class Position(val row: Int, val col: Int) {
    init {
        require(row in POSITION_RANGE) { ROW_OUT_OF_RANGE_ERROR_MESSAGE }
        require(col in POSITION_RANGE) { COLUMN_OUT_OF_RANGE_ERROR_MESSAGE }
    }

    fun checkStraight(
        stones: Stones,
        direction: Pair<Int, Int>,
        weight: Int,
    ): Int {
        var count = 1
        val (startRow, startCol) = Pair(row, col)
        val (rowMoveStep, colMoveStep) = Pair(direction.first * weight, direction.second * weight)
        var (curRow, curCol) = Pair(startRow + rowMoveStep, startCol + colMoveStep)

        while (inRange(curRow, curCol) && stones.isPlaced(Stone.of(curRow, curCol))) {
            count++
            curRow += rowMoveStep
            curCol += colMoveStep
        }
        return count
    }

    private fun inRange(x: Int, y: Int) = x in POSITION_RANGE && y in POSITION_RANGE

    companion object {
        private const val MIN_BOUND = 1
        private const val MAX_BOUND = 15
        val POSITION_RANGE = (MIN_BOUND..MAX_BOUND)
        private const val ROW_OUT_OF_RANGE_ERROR_MESSAGE = "행의 범위는 ${MIN_BOUND}부터 ${MAX_BOUND}입니다"
        private const val COLUMN_OUT_OF_RANGE_ERROR_MESSAGE = "열의 범위는 ${MIN_BOUND}부터 ${MAX_BOUND}입니다"
    }
}
