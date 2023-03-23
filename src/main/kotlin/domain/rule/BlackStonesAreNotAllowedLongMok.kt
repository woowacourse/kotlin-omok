package domain.rule

import domain.*
import domain.state.BlackTurn
import domain.state.State
import kotlin.math.abs

object BlackStonesAreNotAllowedLongMok : DetailRule {
    override fun stateWillObeyThisRule(state: State, nextStone: Stone): Boolean =
        if (state is BlackTurn) !blackStonesWillBeLongMok(state, nextStone) else true

    private fun blackStonesWillBeLongMok(state: State, nextStone: Stone): Boolean =
        Inclination.values().any { (state.blackStones + nextStone).getLinkedStoneCountAt(nextStone.point, it) > 5 }

    private fun Set<Stone>.getLinkedStoneCountAt(point: Point, inclination: Inclination): Int {
        val onePoint = point.getNextBlackStoneIsNotPlacedPoint(this, inclination.directions[0])
        val otherPoint = point.getNextBlackStoneIsNotPlacedPoint(this, inclination.directions[1])

        return Integer.max(abs(onePoint.x - otherPoint.x), abs(onePoint.y - otherPoint.y)) - 1
    }

    private fun Point.getNextBlackStoneIsNotPlacedPoint(blackStones: Set<Stone>, direction: Direction): Point {
        var nextBlankPoint = this
        while (OmokGame.boardContains(nextBlankPoint) && Stone(nextBlankPoint) in blackStones) {
            nextBlankPoint = nextBlankPoint goTo direction
        }
        return nextBlankPoint
    }
}
