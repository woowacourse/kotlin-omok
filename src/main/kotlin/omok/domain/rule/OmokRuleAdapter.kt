package omok.domain.rule

import omok.domain.grid.Column
import omok.domain.grid.OmokGrid.Companion.DEFAULT_SIZE
import omok.domain.grid.OmokPoint
import omok.domain.grid.Row
import rule.facade.BlackRenjuRule

abstract class OmokRuleAdapter {
    protected val dataConverter = DataConverter()
    protected val rule: BlackRenjuRule = BlackRenjuRule(DEFAULT_SIZE, DEFAULT_SIZE)

    abstract fun checkViolation(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): OmokViolation

    fun isWin(
        stones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): Boolean {
        val directions: List<Direction> =
            listOf(
                Direction(0, 1),
                Direction(1, 0),
                Direction(1, 1),
                Direction(1, -1),
            )

        return directions.any { dir ->
            val count = search(dir, stones, latestPoint) + search(-dir, stones, latestPoint) - 1
            count >= WIN_STANDARD
        }
    }

    private fun search(
        direction: Direction,
        stones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): Int {
        val coordinateX = latestPoint.row.value
        val coordinateY = latestPoint.col.value
        var count = 1
        var point = OmokPoint(Row(coordinateX + direction.rowDelta * count), Column(coordinateY + direction.colDelta * count))

        while (checkRange(point.row.value, point.col.value) &&
            stones.find { it == point } != null
        ) {
            count++
            point = OmokPoint(Row(coordinateX + direction.rowDelta * count), Column(coordinateY + direction.colDelta * count))
        }

        return count
    }

    private fun checkRange(
        coordinateX: Int,
        coordinateY: Int,
    ): Boolean {
        return coordinateX in (MIN_BOUND..MAX_BOUND) && coordinateY in (MIN_BOUND..MAX_BOUND)
    }

    companion object {
        protected const val WIN_STANDARD: Int = 5

        private const val MIN_BOUND = 1
        private const val MAX_BOUND = DEFAULT_SIZE
    }
}

operator fun Direction.unaryMinus() = Direction(-rowDelta, -colDelta)
