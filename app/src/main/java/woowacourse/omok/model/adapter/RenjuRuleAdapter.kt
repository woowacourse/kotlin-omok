package woowacourse.omok.model.adapter

import rule.facade.BlackRenjuRule
import woowacourse.omok.model.Board.Companion.MAX_BOARD_HEIGHT
import woowacourse.omok.model.Board.Companion.MAX_BOARD_WIDTH
import woowacourse.omok.model.Direction
import woowacourse.omok.model.game.FoulCondition
import woowacourse.omok.model.stone.Point
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

class RenjuRuleAdapter : RuleAdapter {
    private val blackRenjuRule = BlackRenjuRule(MAX_BOARD_WIDTH, MAX_BOARD_HEIGHT)

    override fun checkAnyFoulCondition(
        stones: Set<Stone>,
        startStone: Stone,
    ): FoulCondition {
        val blackStones: List<Pair<Int, Int>> = stones.filter { it.color == StoneColor.BLACK }.toPairList()
        val whiteStones: List<Pair<Int, Int>> = stones.filter { it.color == StoneColor.WHITE }.toPairList()
        val lastStone = startStone.toPair()
        return when {
            blackRenjuRule.checkOverline(blackStones, lastStone) -> FoulCondition.OVERLINE
            blackRenjuRule.checkDoubleFourFoul(blackStones, whiteStones, lastStone) -> FoulCondition.DOUBLE_FOUR
            blackRenjuRule.checkDoubleThreeFoul(blackStones, whiteStones, lastStone) -> FoulCondition.DOUBLE_THREE
            else -> FoulCondition.NONE
        }
    }

    override fun checkWin(
        stones: Set<Stone>,
        startStone: Stone,
    ): Boolean {
        val blackStones: List<Pair<Int, Int>> = stones.filter { it.color == StoneColor.BLACK }.toPairList()
        val whiteStones: List<Pair<Int, Int>> = stones.filter { it.color == StoneColor.WHITE }.toPairList()
        val lastStone = startStone.toPair()

        return when (startStone.color) {
            StoneColor.BLACK -> checkBlackWin(blackStones, whiteStones, lastStone)
            StoneColor.WHITE -> checkWhiteWin(whiteStones, lastStone)
        }
    }

    private fun checkBlackWin(
        blackStones: List<Pair<Int, Int>>,
        whiteStones: List<Pair<Int, Int>>,
        startStone: Pair<Int, Int>,
        sameStoneToCheck: Int = OMOK_SIZE,
    ): Boolean =
        blackRenjuRule.checkWin(
            blackStones,
            whiteStones,
            startStone,
            sameStoneToCheck,
        )

    private fun checkWhiteWin(
        whiteStones: List<Pair<Int, Int>>,
        lastStone: Pair<Int, Int>,
    ): Boolean {
        val points: Set<Point> = whiteStones.map { Point(it.first, it.second) }.toSet()
        val lastPoint = Point(lastStone.first, lastStone.second)
        return Direction.directionPairs.any { isSerialOmok(points, lastPoint, it) }
    }

    private fun isSerialOmok(
        points: Set<Point>,
        lastPoint: Point,
        directions: Pair<Direction, Direction>,
    ): Boolean {
        val forwardCount = count(points, lastPoint, directions.first)
        val backwardCount = count(points, lastPoint, directions.second)
        return forwardCount + backwardCount >= OMOK_SIZE - 1
    }

    private fun count(
        points: Set<Point>,
        point: Point,
        direction: Direction,
    ): Int {
        val boardRange = 1..<MAX_BOARD_HEIGHT
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
        private const val OMOK_SIZE = 5
    }
}
