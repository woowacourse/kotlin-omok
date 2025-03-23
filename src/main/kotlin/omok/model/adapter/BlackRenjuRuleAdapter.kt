package omok.model.adapter

import omok.model.Point
import rule.facade.BlackRenjuRule

class BlackRenjuRuleAdapter(
    private val blackRenjuRule: BlackRenjuRule
) {
    fun checkDoubleThreeFoul(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
        startPoint: Point,
    ): Boolean {
        return blackRenjuRule.checkDoubleThreeFoul(
            blackPoints.toPairList(),
            whitePoints.toPairList(),
            startPoint.toPair()
        )
    }

    fun checkDoubleFourFoul(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
        startPoint: Point,
    ): Boolean {
        return blackRenjuRule.checkDoubleFourFoul(
            blackPoints.toPairList(),
            whitePoints.toPairList(),
            startPoint.toPair()
        )
    }

    fun checkOverline(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
        startPoint: Point,
    ): Boolean {
        return blackRenjuRule.checkOverline(
            blackPoints.toPairList(),
            startPoint.toPair()
        )
    }

    fun checkWin(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
        startPoint: Point,
        sameStoneToCheck: Int
    ): Boolean {
        return blackRenjuRule.checkWin(
            blackPoints.toPairList(),
            whitePoints.toPairList(),
            startPoint.toPair(),
            sameStoneToCheck
        )
    }

    private fun Point.toPair(): Pair<Int, Int> {
        return row to col
    }

    private fun Set<Point>.toPairList(): List<Pair<Int, Int>> {
        return map { it.toPair() }
    }
}