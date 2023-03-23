package domain.rule

import domain.*
import domain.state.BlackTurn
import domain.state.State
import kotlin.math.abs

object BlackStonesAreNotAllowedLongMok : DetailRule {
    override fun stateWillObeyThisRule(state: State, nextStone: Stone): Boolean =
        if (state is BlackTurn) !isBlackLongMok(state.blackStones, nextStone) else true

    private fun isBlackLongMok(blackStones: Set<Stone>, nextStone: Stone): Boolean =
        Inclination.values().any { 최근_놓인_돌에서_다음_기울기로_연속되는_흑돌_개수(blackStones + nextStone, nextStone, it) > 5 }

    private fun 최근_놓인_돌에서_다음_기울기로_연속되는_흑돌_개수(
        blackStones: Set<Stone>,
        justPlacedStone: Stone,
        inclination: Inclination,
    ): Int {
        val (leftX, leftY) = 다음_방향으로_흑돌을_타고_갔을_때_다음_빈칸(
            blackStones,
            justPlacedStone,
            inclination.directions[0],
        )
        val (rightX, rightY) = 다음_방향으로_흑돌을_타고_갔을_때_다음_빈칸(
            blackStones,
            justPlacedStone,
            inclination.directions[1],
        )
        return Integer.max(abs(rightX - leftX), abs(rightY - leftY)) - 1
    }

    private fun 다음_방향으로_흑돌을_타고_갔을_때_다음_빈칸(
        blackStones: Set<Stone>,
        justPlacedStone: Stone,
        direction: Direction,
    ): Pair<Char, Int> {
        var (nextX, nextY) = justPlacedStone.x to justPlacedStone.y
        while (Point(nextX, nextY) in Board && Stone(nextX, nextY) in blackStones) {
            nextX += direction.dx
            nextY += direction.dy
        }
        return nextX to nextY
    }
}
