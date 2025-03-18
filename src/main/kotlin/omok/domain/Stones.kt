package omok.domain

class Stones(
    points: Set<Point> = emptySet(),
    private val color: StoneColor,
) {
    private val _points = points.toMutableSet()
    val points = _points.toSet()

    operator fun plus(point: Point): Stones = Stones(_points + point, color)

    fun lastStonePoint(): Point = _points.last()

    fun isOmok(): Boolean {
        val lastPoint = lastStonePoint()
        return isHorizontalOmok(lastPoint) ||
            isVerticalOmok(lastPoint) ||
            isDiagonalUpOmok(lastPoint) ||
            isDiagonalDownOmok(lastPoint)
    }

    private fun isHorizontalOmok(lastPoint: Point): Boolean = countConnected(lastPoint, -1, 0) + countConnected(lastPoint, +1, 0) >= 4

    private fun isVerticalOmok(lastPoint: Point): Boolean = countConnected(lastPoint, 0, -1) + countConnected(lastPoint, 0, +1) >= 4

    private fun isDiagonalUpOmok(lastPoint: Point): Boolean = countConnected(lastPoint, -1, -1) + countConnected(lastPoint, +1, +1) >= 4

    private fun isDiagonalDownOmok(lastPoint: Point): Boolean = countConnected(lastPoint, -1, +1) + countConnected(lastPoint, +1, -1) >= 4

    private fun countConnected(
        point: Point,
        dx: Int,
        dy: Int,
    ): Int {
        val boardRange = 0..14

        var count = 0
        var (x, y) = point.x + dx to point.y + dy
        while (x in boardRange && y in boardRange && Point(x, y) in _points) {
            count++
            x += dx
            y += dy
        }
        return count
    }
}
