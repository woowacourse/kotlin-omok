package omok.model.rule

import omok.model.stone.StoneState
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row

class NormalOmokRule(
    private val boardSize: Int,
) {
    fun isPositionOmok(
        stonesMap: Map<Position, StoneState>,
        position: Position,
        checkExactlyFiveStone: Boolean = false,
    ): Boolean {
        if (stonePlacedState(stonesMap, position) == StoneState.NONE) {
            return false
        }

        val totalCount = calculateTotalCount(stonesMap, position)
        if (checkExactlyFiveStone) return 5 in totalCount
        return totalCount.max() >= 5
    }

    private fun stonePlacedState(
        stonesMap: Map<Position, StoneState>,
        position: Position,
    ): StoneState = stonesMap[position] ?: StoneState.NONE

    private fun calculateTotalCount(
        stonesMap: Map<Position, StoneState>,
        position: Position,
    ): List<Int> =
        Direction.lineDirections().map { (directionA, directionB) ->
            val countA = countConnected(stonesMap, position, directionA)
            val countB = countConnected(stonesMap, position, directionB)

            countA + countB + 1
        }

    private fun countConnected(
        stonesMap: Map<Position, StoneState>,
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
}
