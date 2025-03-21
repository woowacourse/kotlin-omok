package omok.model.adapter

import omok.model.Point
import rule.OmokRule
import rule.type.Violation

abstract class OmokRuleAdapter(
    private val omokRule: OmokRule
) {
    fun checkAnyFoulCondition(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
        startPoint: Point,
    ): Violation {
        return omokRule.checkAnyFoulCondition(
            blackPoints.toExternalPointList(),
            whitePoints.toExternalPointList(),
            startPoint.toExternalPoint()
        )
    }

    fun checkSerialSameStonesBiDirection(
        stonePoints: Set<Point>,
        startPoint: Point,
        sameStoneToCheck: Int
    ): Boolean {
        return omokRule.checkSerialSameStonesBiDirection(
            stonePoints.toExternalPointList(),
            startPoint.toExternalPoint(),
            sameStoneToCheck
        )
    }

    private fun Point.toExternalPoint(): rule.wrapper.point.Point {
        return rule.wrapper.point.Point(this.row, this.col)
    }

    private fun Set<Point>.toExternalPointList(): List<rule.wrapper.point.Point> {
        return this.map { rule.wrapper.point.Point(it.row, it.col) }
    }
}