package omok.domain

class Stones(
    stones: List<Stone> = emptyList(),
) {
    private val _stones = stones.toMutableList()
    val stones = _stones.toList()

    operator fun plus(stone: Stone): Stones = Stones(_stones + stone)

    fun lastStonePoint(): Point = _stones.last().point

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
        val pointSet = _stones.map { it.point }.toSet()

        var count = 0
        var (x, y) = point.x + dx to point.y + dy
        while (x in boardRange && y in boardRange && Point(x, y) in pointSet) {
            count++
            x += dx
            y += dy
        }
        return count
    }
}
