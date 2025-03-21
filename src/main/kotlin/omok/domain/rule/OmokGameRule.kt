package omok.domain.rule

import omok.domain.Point
import rule.OmokRule
import rule.wrapper.point.Point as ExternalPoint

abstract class OmokGameRule(
    private val boardSize: Int,
) {
    protected abstract val renjuRule: OmokRule

    fun isFoul(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
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
        Direction.getDirectionPair().any {
            isSerialOmok(points, lastPoint, it)
        }

    private fun isSerialOmok(
        points: Set<Point>,
        lastPoint: Point,
        directions: Pair<Direction, Direction>,
    ): Boolean {
        val forwardCount = countConnected(points, lastPoint, directions.first)
        val backwardCount = countConnected(points, lastPoint, directions.second)
        return forwardCount + backwardCount >= OMOK_STONE_COUNT - 1
    }

    private fun countConnected(
        points: Set<Point>,
        point: Point,
        direction: Direction,
    ): Int {
        val boardRange = 0 until boardSize
        var count = 0

        val (dx, dy) = direction.x to direction.y
        var (x, y) = point.row + dx to point.col + dy
        while (x in boardRange && y in boardRange && Point(x, y) in points) {
            count++
            x += dx
            y += dy
        }
        return count
    }

    private fun Point.toExternalPoint() = ExternalPoint(this.row, this.col)

    companion object {
        private const val OMOK_STONE_COUNT = 5
    }
}
