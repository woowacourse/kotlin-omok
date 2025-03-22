package omok.domain.rule

import omok.domain.OmokGrid
import omok.domain.OmokViolation
import omok.domain.StoneColor
import omok.domain.point.OmokPoint

class Referee {
    fun checkViolation(
        stoneColor: StoneColor,
        grid: OmokGrid,
        latestPoint: OmokPoint,
    ) {
        val ruleAdapter = getRule(stoneColor)
        val violation =
            listOf(
                ruleAdapter.checkViolation(grid.blackStones.stones, grid.whiteStones.stones, latestPoint),
                checkDuplicateMove(grid.getTotalStones(), latestPoint),
            ).lastOrNull { it.isError } ?: OmokViolation.NONE
        dealViolation(violation)
    }

    fun checkWin(
        stoneColor: StoneColor,
        stones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): Boolean {
        val ruleAdapter = getRule(stoneColor)
        return ruleAdapter.isWin(stones, latestPoint, 5)
    }

    private fun checkDuplicateMove(
        totalStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): OmokViolation {
        if (totalStones.contains(latestPoint)) return OmokViolation.OCCUPIED
        return OmokViolation.NONE
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

    private fun getRule(stoneColor: StoneColor): OmokRuleAdapter {
        return when (stoneColor) {
            StoneColor.WHITE -> WhiteRuleAdapterImpl
            StoneColor.BLACK -> BlackRuleAdapterImpl
        }
    }

    companion object {
        private const val ERROR_DOUBLE_THREE = "3x3 위치에 놓을 수 없습니다"
        private const val ERROR_DOUBLE_FOUR = "4x4 위치에 놓을 수 없습니다"
        private const val ERROR_OVER_LINE = "장목 위치에 놓을 수 없습니다"
        private const val ERROR_DUPLICATE_MOVE = "이미 돌이 있습니다"
    }
}
