package omok.domain.rule

import omok.domain.point.OmokPoint

class DataConverter {
    fun convertOmokPointToPoint(omokPoint: OmokPoint): Pair<Int, Int> {
        return Pair(omokPoint.row.value, omokPoint.col.value)
    }

    fun convertSetToList(stones: Set<OmokPoint>): List<Pair<Int, Int>> {
        return stones.toList().map { convertOmokPointToPoint(it) }
    }
}
