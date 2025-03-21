package omok.domain.rule

import omok.domain.OmokViolation
import omok.domain.point.OmokPoint
import rule.OmokRule
import rule.WhiteRenjuRule

object WhiteRuleAdapterImpl : OmokRuleAdapter() {
    override val rule: OmokRule = WhiteRenjuRule()

    override fun checkViolation(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): OmokViolation {
        return OmokViolation.NONE
    }
}
