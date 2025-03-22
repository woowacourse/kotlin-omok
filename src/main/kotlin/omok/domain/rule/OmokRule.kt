package omok.domain.rule

import omok.domain.Point

abstract class OmokRule(
    private val boardSize: Int,
) {
    abstract fun isFoul(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
        startPoint: Point,
    ): Boolean

    fun isOmok(
        points: Set<Point>,
        lastPoint: Point,
    ): Boolean = Direction.directionPairs.any { isSerialOmok(points, lastPoint, it) }

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

    companion object {
        private const val OMOK_STONE_COUNT = 5
    }
}
