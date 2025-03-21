package omok.domain.rule

import omok.domain.point.OmokPoint
import rule.OmokRule
import rule.WhiteRenjuRule
import rule.type.Violation

object WhiteRuleAdapterImpl : OmokRuleAdapter() {
    override val rule: OmokRule = WhiteRenjuRule()

    override fun checkViolation(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): Violation {
        return Violation.NONE
    }
}
