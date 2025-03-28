package woowacourse.omok.domain

import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.grid.Stone
import woowacourse.omok.domain.rule.OmokRuleAdapter
import woowacourse.omok.domain.rule.ValidationResult

class Referee {
    fun checkViolation(
        ruleAdapter: OmokRuleAdapter,
        thisStones: Set<Stone>,
        otherStones: Set<Stone>,
        latestPoint: Stone,
    ): ValidationResult {
        return listOf(
            ruleAdapter.checkViolation(thisStones, otherStones, latestPoint),
            checkDuplicateMove((thisStones + otherStones).map { it.point }.toSet(), latestPoint.point),
        ).lastOrNull { it != ValidationResult.Success } ?: ValidationResult.Success
    }

    private fun checkDuplicateMove(
        totalStones: Set<Point>,
        latestPoint: Point,
    ): ValidationResult {
        if (totalStones.contains(latestPoint)) return ValidationResult.Failure.Occupied
        return ValidationResult.Success
    }

    fun checkWin(
        ruleAdapter: OmokRuleAdapter,
        stones: Set<Stone>,
        latestPoint: Stone,
    ): Boolean {
        return ruleAdapter.isWin(stones, latestPoint)
    }
}
