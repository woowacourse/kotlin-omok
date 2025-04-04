package woowacourse.omok.domain.rule

import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.OmokGrid.Companion.DEFAULT_SIZE
import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.grid.Row
import woowacourse.omok.domain.grid.Stone

abstract class OmokRuleAdapter {
    abstract fun checkViolation(
        thisStones: Set<Stone>,
        otherStones: Set<Stone>,
        latestStone: Stone,
    ): ValidationResult

    fun isWin(
        stones: Set<Stone>,
        latestStone: Stone,
    ): Boolean {
        val directions: List<Direction> =
            listOf(
                Direction(0, 1),
                Direction(1, 0),
                Direction(1, 1),
                Direction(1, -1),
            )

        return directions.any { dir ->
            val count = search(dir, stones, latestStone) + search(-dir, stones, latestStone) - 1
            count >= WIN_STANDARD
        }
    }

    private fun search(
        direction: Direction,
        stones: Set<Stone>,
        latestStone: Stone,
    ): Int {
        val coordinateX = latestStone.point.row.value
        val coordinateY = latestStone.point.col.value
        var count = MIN_BOUND

        while (true) {
            val nextX = coordinateX + direction.rowDelta * count
            val nextY = coordinateY + direction.colDelta * count
            if (!checkRange(nextX, nextY)) break

            val stone = Stone(Point(Row(nextX), Column(nextY)), latestStone.stoneColor)
            if (stone !in stones) break

            count++
        }

        return count
    }

    private fun checkRange(
        coordinateX: Int,
        coordinateY: Int,
    ): Boolean {
        return coordinateX in (MIN_BOUND..MAX_BOUND) && coordinateY in (MIN_BOUND..MAX_BOUND)
    }

    operator fun Direction.unaryMinus() = Direction(-rowDelta, -colDelta)

    companion object {
        protected const val WIN_STANDARD: Int = 5

        private const val MIN_BOUND = 1
        private const val MAX_BOUND = DEFAULT_SIZE
    }
}
