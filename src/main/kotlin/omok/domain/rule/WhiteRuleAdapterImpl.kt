package omok.domain.rule

import omok.domain.OmokViolation
import omok.domain.point.OmokPoint

object WhiteRuleAdapterImpl : OmokRuleAdapter() {
    override fun checkViolation(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): OmokViolation {
        return OmokViolation.NONE
    }
}
