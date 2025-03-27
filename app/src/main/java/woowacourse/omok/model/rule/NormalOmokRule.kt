package woowacourse.omok.model.rule

import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.position.Col
import woowacourse.omok.model.stone.position.Position
import woowacourse.omok.model.stone.position.Row

class NormalOmokRule(
    private val boardSize: Int,
) {
    fun isPositionOmok(
        stonesMap: Map<Position, StoneColor>,
        position: Position,
        checkExactlyFiveStone: Boolean = false,
    ): Boolean {
        val totalCount = calculateTotalCount(stonesMap, position)
        if (checkExactlyFiveStone) return OMOK_WIN_NUMBER in totalCount
        return totalCount.max() >= OMOK_WIN_NUMBER
    }

    private fun calculateTotalCount(
        stonesMap: Map<Position, StoneColor>,
        position: Position,
    ): List<Int> =
        Direction.lineDirections().map { (directionA, directionB) ->
            val countDirectionAStones = countConnected(stonesMap, position, directionA)
            val countDirectionBStones = countConnected(stonesMap, position, directionB)

            countDirectionAStones + countDirectionBStones + COUNT_CURRENT_STONE
        }

    private fun countConnected(
        stonesMap: Map<Position, StoneColor>,
        position: Position,
        direction: Direction,
    ): Int {
        var count = 0
        var x = position.row.value + direction.dx
        var y = position.col.value + direction.dy

        while (x in 0 until boardSize && y in 0 until boardSize) {
            val nextPos = Position(Row(x), Col(y))
            if (stonesMap[nextPos] == stonesMap[position]) {
                count++
                x += direction.dx
                y += direction.dy
            } else {
                break
            }
        }
        return count
    }

    companion object {
        private const val OMOK_WIN_NUMBER = 5
        private const val COUNT_CURRENT_STONE = 1
    }
}
