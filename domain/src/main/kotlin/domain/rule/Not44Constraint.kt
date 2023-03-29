package domain.rule

import domain.Board
import domain.Inclination
import domain.stone.*

object Not44Constraint : StonesConstraint {

    override fun isSatisfied(stones: Stones, forbiddenPoints: Set<Point>): Boolean {
        val lastPoint = stones.lastPoint ?: return true
        var count44 = 0
        Inclination.values().forEach {
            count44 += stones.getCount4At(lastPoint, it, forbiddenPoints)
        }
        return count44 < 2
    }

    private fun Stones.getCount4At(
        point: Point,
        inclination: Inclination,
        forbiddenPoints: Set<Point>,
    ): Int {
        if (this.isHalfOpen4At(point, inclination, forbiddenPoints)) {
            return 1
        }
        return listOf(inclination.oneDirection, inclination.otherDirection).count {
            this.willCompleteOmokIfStonePlaceAt(this.getNextBlankPointAt(point, it), inclination)
        }
    }

    private fun Stones.isHalfOpen4At(
        point: Point,
        inclination: Inclination,
        forbiddenPoints: Set<Point>,
    ): Boolean {
        val onePoint = this.getNextBlankPointAt(point, inclination.oneDirection)
        val otherPoint = this.getNextBlankPointAt(point, inclination.otherDirection)

        if (onePoint.isPlaceableOnBoard(forbiddenPoints) ||
            otherPoint.isPlaceableOnBoard(forbiddenPoints)
        ) {
            return this.getLinkedStonesCountAt(point, inclination) == 4
        }
        return false
    }

    private fun Point.isPlaceableOnBoard(forbiddenPoints: Set<Point>): Boolean =
        Board.pointIsWithinBoardRange(this) && this !in forbiddenPoints

    private fun Stones.willCompleteOmokIfStonePlaceAt(
        point: Point,
        inclination: Inclination,
    ): Boolean {
        val nextStones = this.copy().apply { add(Stone(point)) }
        return nextStones.getLinkedStonesCountAt(point, inclination) == 5
    }
}
