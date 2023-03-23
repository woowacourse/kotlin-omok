package domain.rule

import domain.*
import domain.state.BlackTurn
import domain.state.State
import kotlin.math.abs

object BlackStonesAreNotAllowed33Rule : DetailRule {
    override fun stateWillObeyThisRule(state: State, nextStone: Stone): Boolean =
        if (state is BlackTurn) !blackStonesWillBe33(state, nextStone) else true

    private fun blackStonesWillBe33(state: State, nextStone: Stone): Boolean {
        val nextBlackStones = state.blackStones + nextStone
        var count33 = 0
        Inclination.values().forEach { if (nextBlackStones.canBeOpen4At(nextStone.point, it, state)) count33++ }
        return count33 >= 2
    }

    private fun Set<Stone>.canBeOpen4At(point: Point, inclination: Inclination, state: State): Boolean {
        return inclination.directions.any {
            val nextPoint = point.getNextBlackStoneIsNotPlacedPoint(this, it)
            if (nextPoint.canPlaceStoneWhenInThis(state)) {
                (this + Stone(nextPoint)).isOpen4At(point, inclination, state)
            } else {
                false
            }
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

    private fun Set<Stone>.isOpen4At(point: Point, inclination: Inclination, state: State): Boolean {
        val nextPoint1 = point.getNextBlackStoneIsNotPlacedPoint(this, inclination.directions[0])
        val nextPoint2 = point.getNextBlackStoneIsNotPlacedPoint(this, inclination.directions[1])

        if (nextPoint1.canPlaceStoneWhenInThis(state) && nextPoint2.canPlaceStoneWhenInThis(state)) {
            return abs(nextPoint1.x - nextPoint2.x) == 5 || abs(nextPoint1.y - nextPoint2.y) == 5
        }
        return false
    }
}
