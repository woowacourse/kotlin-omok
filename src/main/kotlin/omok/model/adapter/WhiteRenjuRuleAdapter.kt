package omok.model.adapter

import omok.model.Direction
import omok.model.game.FoulCondition
import omok.model.stone.Point
import omok.model.stone.Stone

class WhiteRenjuRuleAdapter(
    boardWidth: Int = 15,
    boardHeight: Int = 15,
) : RuleAdapter {
    private val boardSize = minOf(boardWidth, boardHeight)

    override fun checkAnyFoulCondition(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
    ): FoulCondition = FoulCondition.NONE

    override fun checkWin(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
        sameStoneToCheck: Int,
    ): Boolean = isOmok(whiteStones.map { it.point }.toSet(), startStone.point)

    private fun isOmok(
        points: Set<Point>,
        lastPoint: Point,
    ): Boolean = Direction.directionPairs.any { isSerialOmok(points, lastPoint, it) }

    private fun isSerialOmok(
        points: Set<Point>,
        lastPoint: Point,
        directions: Pair<Direction, Direction>,
    ): Boolean {
        val forwardCount = count(points, lastPoint, directions.first)
        val backwardCount = count(points, lastPoint, directions.second)
        return forwardCount + backwardCount >= OMOK_STONE_COUNT - 1
    }

    private fun count(
        points: Set<Point>,
        point: Point,
        direction: Direction,
    ): Int {
        val boardRange = 1..<boardSize
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
