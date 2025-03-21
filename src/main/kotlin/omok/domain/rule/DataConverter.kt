package omok.domain.rule

import omok.domain.point.OmokPoint
import rule.wrapper.point.Point

class DataConverter {
    fun convertOmokPointToPoint(omokPoint: OmokPoint): Point {
        return Point(omokPoint.row.value, omokPoint.col.value)
    }

    fun convertSetToList(stones: Set<OmokPoint>): List<Point> {
        return stones.toList().map { convertOmokPointToPoint(it) }
    }
}
