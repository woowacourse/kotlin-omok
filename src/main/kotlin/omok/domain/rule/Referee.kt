package omok.domain.rule

import omok.domain.OmokGrid
import omok.domain.point.OmokPoint
import rule.type.Violation

class Referee {
    fun checkViolation(
        ruleAdapter: OmokRuleAdapter,
        grid: OmokGrid,
        latestPoint: OmokPoint,
    ): Violation {
        return ruleAdapter.checkViolation(grid.blackStones.stones, grid.whiteStones.stones, latestPoint)
    }

    fun checkWin(
        ruleAdapter: OmokRuleAdapter,
        stones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): Boolean {
        return ruleAdapter.isWin(stones, latestPoint, 5)
    }
}
