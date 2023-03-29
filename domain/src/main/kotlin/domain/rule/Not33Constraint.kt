package domain.rule

import domain.Board
import domain.Inclination
import domain.stone.*

object Not33Constraint : StonesConstraint {

    override fun isSatisfied(stones: Stones, forbiddenPoints: Set<Point>): Boolean {
        val lastPoint = stones.lastPoint ?: return true
        var count33 = 0
        Inclination.values().forEach {
            if (stones.canBeOpen4At(lastPoint, it, forbiddenPoints)) count33++
        }
        return count33 < 2
    }

    private fun Stones.canBeOpen4At(
        point: Point,
        inclination: Inclination,
        forbiddenPoints: Set<Point>,
    ): Boolean {
        val onePoint = this.getNextBlankPointAt(point, inclination.oneDirection)
        val otherPoint = this.getNextBlankPointAt(point, inclination.otherDirection)

        return this.willBeOpen4IfStonePlaceAt(onePoint, inclination, forbiddenPoints) ||
            this.willBeOpen4IfStonePlaceAt(otherPoint, inclination, forbiddenPoints)
    }

    private fun Stones.willBeOpen4IfStonePlaceAt(
        point: Point,
        inclination: Inclination,
        forbiddenPoints: Set<Point>,
    ): Boolean {
        if (point !in forbiddenPoints) {
            val nextStones: Stones = this.copy().apply { add(Stone(point)) }
            return nextStones.isOpen4At(point, inclination, forbiddenPoints)
        }
        return false
    }

    private fun Stones.isOpen4At(
        point: Point,
        inclination: Inclination,
        forbiddenPoints: Set<Point>,
    ): Boolean {
        val onePoint = this.getNextBlankPointAt(point, inclination.oneDirection)
        val otherPoint = this.getNextBlankPointAt(point, inclination.otherDirection)

        if (onePoint.isPlaceableOnBoard(forbiddenPoints) &&
            otherPoint.isPlaceableOnBoard(forbiddenPoints)
        ) {
            return this.getLinkedStonesCountAt(point, inclination) == 4
        }
        return false
    }

    private fun Point.isPlaceableOnBoard(forbiddenPoints: Set<Point>): Boolean =
        Board.pointIsWithinBoardRange(this) && this !in forbiddenPoints
}
