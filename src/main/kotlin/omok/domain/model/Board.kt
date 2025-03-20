package omok.domain.model

import omok.domain.model.position.Column
import omok.domain.model.position.Direction
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType

class Board(
    val stones: Map<Position, StoneType> = mapOf(),
    val size: Int = DEFAULT_BOARD_SIZE,
) {
    fun placeStone(
        position: Position,
        stoneType: StoneType,
    ): Board {
        require(canPlace(position)) { "바둑돌이 있는 곳에는 둘 수 없습니다." }
        return Board(stones + mapOf(position to stoneType))
    }

    fun canPlace(position: Position): Boolean = stones[position] == null

    fun lastStoneOrNull(): OmokStone? {
        val lastKey = stones.keys.lastOrNull() ?: return null
        val lastValue = stones[lastKey] ?: return null
        return OmokStone(lastKey, lastValue)
    }

    fun getStones(stoneType: StoneType): List<OmokStone> {
        return stones.filterValues { it == stoneType }
            .map { OmokStone(it.key, stoneType) }
    }

    fun checkWin(): Boolean {
        val directions =
            listOf(
                Direction(0, 1),
                Direction(1, 0),
                Direction(1, 1),
                Direction(1, -1),
            )

        return stones.entries.any { (position, stone) ->
            directions.any { direction ->
                direction.countConsecutive(position, stone) >= 5
            }
        }
    }

    private fun Direction.countConsecutive(
        position: Position,
        stoneType: StoneType,
    ): Int {
        return countInDirection(position, stoneType, this) + countInDirection(position, stoneType, this.inverse()) - 1
    }

    private fun countInDirection(
        position: Position,
        stoneType: StoneType,
        direction: Direction,
    ): Int {
        var count = 1
        var currentPos = position

        while (true) {
            val nextPos = currentPos.next(direction) ?: break
            if (stones[nextPos] == stoneType) {
                count++
                currentPos = nextPos
            } else {
                break
            }
        }

        return count
    }

    private fun Position.next(direction: Direction): Position? {
        val nextRowValue = this.row.value + direction.rowDelta
        val nextColIndex = this.column.value - 1 + direction.colDelta

        if (nextRowValue !in 1..15 || nextColIndex !in 0..14) return null

        val nextColumn = Column.from(('A' + nextColIndex))
        val nextRow = Row(nextRowValue)
        return Position(nextColumn, nextRow)
    }

    companion object {
        private const val DEFAULT_BOARD_SIZE = 15
    }
}
