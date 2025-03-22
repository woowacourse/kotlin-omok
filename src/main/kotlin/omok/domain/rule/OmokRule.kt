package omok.domain.rule

import omok.domain.Point

class OmokRule(private val points: Set<Point>) {
    fun isOmok(
        lastPoint: Point,
        size: Int,
    ): Boolean =
        listOf(HORIZONTAL, VERTICAL, DIAGONAL_UP, DIAGONAL_DOWN).any {
            isSerialOmok(lastPoint, it, size)
        }

    private fun isSerialOmok(
        lastPoint: Point,
        directions: List<Pair<Int, Int>>,
        size: Int,
    ): Boolean = directions.sumOf { countConnected(lastPoint, it, size) } >= OMOK_STONE_COUNT - 1

    private fun countConnected(
        point: Point,
        direction: Pair<Int, Int>,
        size: Int,
    ): Int {
        val boardRange = 0..<size
        var count = 0

        val (dx, dy) = direction
        var (x, y) = point.x + dx to point.y + dy
        while (x in boardRange && y in boardRange && Point(x, y) in points) {
            count++
            x += dx
            y += dy
        }
        return count
    }

    companion object {
        private const val OMOK_STONE_COUNT = 5

        private val HORIZONTAL = listOf(Pair(-1, 0), Pair(1, 0))
        private val VERTICAL = listOf(Pair(0, 1), Pair(0, -1))
        private val DIAGONAL_UP = listOf(Pair(-1, -1), Pair(1, 1))
        private val DIAGONAL_DOWN = listOf(Pair(-1, 1), Pair(1, -1))
    }
}