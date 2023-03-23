package domain.rule

import domain.*
import domain.state.BlackTurn
import domain.state.State
import domain.stone.Point
import domain.stone.Stone
import kotlin.math.abs

object BlackStonesAreNotAllowed44Rule : DetailRule {
    override fun stateWillObeyThisRule(state: State, nextStone: Stone): Boolean =
        if (state is BlackTurn) !blackStonesWillBe44(state, nextStone) else true

    private fun blackStonesWillBe44(state: State, nextStone: Stone): Boolean {
        val nextBlackStones = state.blackStones + nextStone
        var count44 = 0
        Inclination.values().forEach {
            if (nextBlackStones.canBeHalfOpen4At(nextStone.point, it, state)) {
                count44++
            } else {
                count44 += it.directions.count { direction ->
                    nextBlackStones.willCompleteOmokIfPutStoneAt(nextStone.point, direction, state)
                }
            }
        }
        return count44 >= 2
    }

    private fun Set<Stone>.canBeHalfOpen4At(point: Point, inclination: Inclination, state: State): Boolean {
        val onePoint = point.getNextBlackStoneIsNotPlacedPoint(this, inclination.directions[0])
        val otherPoint = point.getNextBlackStoneIsNotPlacedPoint(this, inclination.directions[1])

        if (onePoint.canPlaceStoneWhenInThis(state) || otherPoint.canPlaceStoneWhenInThis(state)) {
            return Integer.max(abs(onePoint.x - otherPoint.x), abs(onePoint.y - otherPoint.y)) == 5
        }
        return false
    }

    private fun Set<Stone>.willCompleteOmokIfPutStoneAt(point: Point, direction: Direction, state: State): Boolean {
        val nextPoint = point.getNextBlackStoneIsNotPlacedPoint(this, direction)

        return if (nextPoint.canPlaceStoneWhenInThis(state)) {
            (this + Stone(nextPoint)).getLinkedStoneCountAt(nextPoint, direction.getInclination()) == 5
        } else {
            false
        }
    }

    private fun Point.canPlaceStoneWhenInThis(state: State): Boolean {
        val stone = Stone(this)
        return OmokGame.boardContains(this) && stone !in state.blackStones && stone !in state.whiteStones
    }

    private fun Point.getNextBlackStoneIsNotPlacedPoint(blackStones: Set<Stone>, direction: Direction): Point {
        var nextBlankPoint = this
        while (OmokGame.boardContains(nextBlankPoint) && Stone(nextBlankPoint) in blackStones) {
            nextBlankPoint = nextBlankPoint goTo direction
        }
        return nextBlankPoint
    }

    private fun Set<Stone>.getLinkedStoneCountAt(point: Point, inclination: Inclination): Int {
        val onePoint = point.getNextBlackStoneIsNotPlacedPoint(this, inclination.directions[0])
        val otherPoint = point.getNextBlackStoneIsNotPlacedPoint(this, inclination.directions[1])

        return Integer.max(abs(onePoint.x - otherPoint.x), abs(onePoint.y - otherPoint.y)) - 1
    }
}
