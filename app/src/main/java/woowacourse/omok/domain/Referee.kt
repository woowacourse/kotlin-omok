package woowacourse.omok.domain

import woowacourse.omok.domain.grid.OmokPoint
import woowacourse.omok.domain.rule.OmokRuleAdapter
import woowacourse.omok.domain.rule.OmokViolation

class Referee {
    fun checkViolation(
        ruleAdapter: OmokRuleAdapter,
        thisStones: Set<OmokPoint>,
        otherStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ) {
        val violation =
            listOf(
                ruleAdapter.checkViolation(thisStones, otherStones, latestPoint),
                checkDuplicateMove(thisStones + otherStones, latestPoint),
            ).lastOrNull { it != OmokViolation.NONE } ?: OmokViolation.NONE
        dealViolation(violation)
    }

    private fun checkDuplicateMove(
        totalStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): OmokViolation {
        if (totalStones.contains(latestPoint)) return OmokViolation.OCCUPIED
        return OmokViolation.NONE
    }

    fun checkWin(
        ruleAdapter: OmokRuleAdapter,
        stones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): Boolean {
        return ruleAdapter.isWin(stones, latestPoint)
    }

    private fun dealViolation(omokViolation: OmokViolation) {
        when (omokViolation) {
            OmokViolation.DOUBLE_THREE -> throw IllegalStateException(ERROR_DOUBLE_THREE)
            OmokViolation.DOUBLE_FOUR -> throw IllegalStateException(ERROR_DOUBLE_FOUR)
            OmokViolation.OVER_LINE -> throw IllegalStateException(ERROR_OVER_LINE)
            OmokViolation.OCCUPIED -> throw IllegalStateException(ERROR_DUPLICATE_MOVE)
            OmokViolation.NONE -> {}
        }
    }

    companion object {
        private const val ERROR_DOUBLE_THREE = "3x3 위치에 놓을 수 없습니다"
        private const val ERROR_DOUBLE_FOUR = "4x4 위치에 놓을 수 없습니다"
        private const val ERROR_OVER_LINE = "장목 위치에 놓을 수 없습니다"
        private const val ERROR_DUPLICATE_MOVE = "이미 돌이 있습니다"
    }
}
