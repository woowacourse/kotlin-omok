package woowacourse.omok.domain

import woowacourse.omok.domain.grid.OmokPoint
import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.rule.MoveResult
import woowacourse.omok.domain.rule.OmokRuleAdapter

class Referee {
    fun checkViolation(
        ruleAdapter: OmokRuleAdapter,
        thisStones: Set<OmokPoint>,
        otherStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): MoveResult {
        return listOf(
            ruleAdapter.checkViolation(thisStones, otherStones, latestPoint),
            checkDuplicateMove((thisStones + otherStones).map { it.point }.toSet(), latestPoint.point),
        ).lastOrNull { it != MoveResult.Success } ?: MoveResult.Success
    }

    private fun checkDuplicateMove(
        totalStones: Set<Point>,
        latestPoint: Point,
    ): MoveResult {
        if (totalStones.contains(latestPoint)) return MoveResult.Failure.Occupied
        return MoveResult.Success
    }

    fun checkWin(
        ruleAdapter: OmokRuleAdapter,
        stones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): Boolean {
        return ruleAdapter.isWin(stones, latestPoint)
    }
}
