package domain.rule

import domain.stone.Point
import domain.stone.Stones

interface StonesConstraint {

    fun isSatisfied(stones: Stones, forbiddenPoints: Set<Point>): Boolean
}