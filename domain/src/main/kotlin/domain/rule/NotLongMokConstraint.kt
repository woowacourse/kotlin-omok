package domain.rule

import domain.Inclination
import domain.stone.Point
import domain.stone.Stones

object NotLongMokConstraint : StonesConstraint {

    override fun isSatisfied(stones: Stones, forbiddenPoints: Set<Point>): Boolean {
        if (stones.lastPoint == null) return true
        return Inclination.values()
            .any { stones.getLinkedStonesCountAt(stones.lastPoint!!, it) > 5 }.not()
    }
}