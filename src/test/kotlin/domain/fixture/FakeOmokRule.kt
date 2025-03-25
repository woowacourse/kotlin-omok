package domain.fixture

import omok.domain.rule.OmokRule
import omok.domain.rule.OmokRules
import omok.domain.rule.finder.DfsRenjuFinder
import omok.domain.rule.renjuRule.RenjuRule

object FakeOmokRule : OmokRules {
    override val rules: List<OmokRule>
        get() = listOf(RenjuRule(DfsRenjuFinder))
}
