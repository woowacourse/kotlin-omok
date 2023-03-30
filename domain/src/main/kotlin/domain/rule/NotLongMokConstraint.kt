package domain.rule

import domain.Inclination
import domain.stone.Point
import domain.stone.Stones

object NotLongMokConstraint : StonesConstraint {

    override fun isSatisfied(stones: Stones, forbiddenPoints: Set<Point>): Boolean {
        val lastPoint = stones.lastPoint ?: return true
        return Inclination.values()
            .any { stones.getLinkedStonesCountAt(lastPoint, it) > 5 }.not()
    }
}
