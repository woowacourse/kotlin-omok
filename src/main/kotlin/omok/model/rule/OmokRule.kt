package omok.model.rule

import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row

class OmokRule(
    private val boardSize: Int,
) {
    fun isLastStoneOmok(
        stonesMap: Map<Position, StoneColor>,
        lastStone: Stone,
    ): Boolean {
        lastStone.let {
            val lastStonePosition = lastStone.position

            val stoneColor = stonesMap[lastStonePosition] ?: return false
            if (stoneColor != lastStone.stoneColor) return false

            val totalCount = calculateTotalCount(stonesMap, lastStone)
            return totalCount >= 5
        }
    }

    private fun calculateTotalCount(
        stonesMap: Map<Position, StoneColor>,
        lastStone: Stone,
    ): Int =
        Direction.lineDirections().maxOf { (directionA, directionB) ->
            val countA = countConnected(stonesMap, lastStone, directionA)
            val countB = countConnected(stonesMap, lastStone, directionB)

            countA + countB + 1
        }

    private fun countConnected(
        stonesMap: Map<Position, StoneColor>,
        stone: Stone,
        direction: Direction,
    ): Int {
        var count = 0
        val stonePosition = stone.position
        var x = stonePosition.row.value + direction.dx
        var y = stonePosition.col.value + direction.dy

        while (x in 0 until boardSize && y in 0 until boardSize) {
            val nextPos = Position(Row(x), Col(y))
            if (stonesMap[nextPos] == stone.stoneColor) {
                count++
                x += direction.dx
                y += direction.dy
            } else {
                break
            }
        }
        return count
    }
}
