package domain.model

class WinningJudgement(private val stones: Map<Position, Stone>) {
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
        stone: Stone,
    ): Int {
        return countInDirection(position, stone, this) + countInDirection(position, stone, this.inverse()) - 1
    }

    private fun countInDirection(
        position: Position,
        stone: Stone,
        direction: Direction,
    ): Int {
        var count = 1
        var currentPos = position

        while (true) {
            val nextPos = currentPos.next(direction) ?: break
            if (stones[nextPos] == stone) {
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
}
