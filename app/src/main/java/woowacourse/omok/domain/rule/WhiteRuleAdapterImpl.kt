package woowacourse.omok.domain.rule

import woowacourse.omok.domain.grid.OmokPoint

object WhiteRuleAdapterImpl : OmokRuleAdapter() {
    override fun checkViolation(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): OmokViolation {
        return OmokViolation.NONE
    }
}
