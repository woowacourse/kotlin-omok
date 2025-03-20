package omok.model

class OmokRule(
    private val boardSize: Int,
) {
    fun isLastStoneOmok(
        stonesMap: Map<Position, StoneState>,
        lastStone: Stone,
    ): Boolean {
        lastStone.let {
            val lastStonePosition = lastStone.position
            if (stonePlacedState(stonesMap, lastStonePosition) == StoneState.NONE) {
                return false
            }

            return calculateTotalCount(stonesMap, lastStone)
        }
    }

    private fun stonePlacedState(
        stonesMap: Map<Position, StoneState>,
        position: Position,
    ): StoneState = stonesMap[position] ?: StoneState.NONE

    private fun countConnected(
        stonesMap: Map<Position, StoneState>,
        stone: Stone,
        direction: Direction,
    ): Int {
        var count = 0
        val stonePosition = stone.position
        var x = stonePosition.row.value + direction.dx
        var y = stonePosition.col.value + direction.dy

        while (x in 0 until boardSize && y in 0 until boardSize) {
            val nextPos = Position(Row(x), Col(y))
            if (stonesMap[nextPos] == stone.stoneState) {
                count++
                x += direction.dx
                y += direction.dy
            } else {
                break
            }
        }
        return count
    }

    private fun calculateTotalCount(
        stonesMap: Map<Position, StoneState>,
        lastStone: Stone,
    ): Boolean {
        Direction.lineDirections().forEach { (directionA, directionB) ->
            val countA = countConnected(stonesMap, lastStone, directionA)
            val countB = countConnected(stonesMap, lastStone, directionB)

            val totalCount = countA + countB + 1
            if (totalCount >= 5) return true
        }
        return false
    }
}
