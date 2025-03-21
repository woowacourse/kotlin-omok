package omok.domain.rule

import omok.domain.Point
import rule.OmokRule

abstract class OmokGameRule(
    private val boardSize: Int,
) {
    abstract val renjuRule: OmokRule

    fun checkAnyFoulCondition(
        blackPoints: List<Point>,
        whitePoints: List<Point>,
        startPoint: Point,
    ): Boolean {
        val violateType =
            renjuRule.checkAnyFoulCondition(
                blackPoints.map { it.toExternalPoint() },
                whitePoints.map { it.toExternalPoint() },
                startPoint.toExternalPoint(),
            )
        return violateType.state
    }

    fun isOmok(
        points: Set<Point>,
        lastPoint: Point,
    ): Boolean =
        listOf(HORIZONTAL, VERTICAL, DIAGONAL_UP, DIAGONAL_DOWN).any {
            isSerialOmok(points, lastPoint, it)
        }

    private fun isSerialOmok(
        points: Set<Point>,
        lastPoint: Point,
        directions: List<Pair<Int, Int>>,
    ): Boolean = directions.sumOf { countConnected(points, lastPoint, it) } >= OMOK_STONE_COUNT - 1

    private fun countConnected(
        points: Set<Point>,
        point: Point,
        direction: Pair<Int, Int>,
    ): Int {
        val boardRange = 0..<boardSize
        var count = 0

        val (dx, dy) = direction
        var (x, y) = point.row + dx to point.col + dy
        while (x in boardRange && y in boardRange && Point(x, y) in points) {
            count++
            x += dx
            y += dy
        }
        return count
    }

    private fun Point.toExternalPoint() = rule.wrapper.point.Point(this.row, this.col)

    companion object {
        private const val OMOK_STONE_COUNT = 5

        private val HORIZONTAL = listOf(Pair(-1, 0), Pair(1, 0))
        private val VERTICAL = listOf(Pair(0, 1), Pair(0, -1))
        private val DIAGONAL_UP = listOf(Pair(-1, -1), Pair(1, 1))
        private val DIAGONAL_DOWN = listOf(Pair(-1, 1), Pair(1, -1))
    }
}
