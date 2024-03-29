package woowacourse.omok.domain.model.rule

import woowacourse.omok.domain.model.rule.library.FourFourRule
import woowacourse.omok.domain.model.rule.library.OmokRule
import woowacourse.omok.domain.model.rule.library.OverlineRule
import woowacourse.omok.domain.model.rule.library.ThreeThreeRule

class ForbiddenRules(rules: List<OmokRule>) {
    constructor(vararg rule: OmokRule) : this(listOf(*rule))

    val rules = rules.toList()

    fun hasNoRule(): Boolean = rules.isEmpty()

    fun hasDoubleRule(): Boolean = rules.any { it is ThreeThreeRule || it is FourFourRule }

    fun hasOverlineRule(): Boolean = rules.any { it is OverlineRule }

    fun initOverlineStandard(continualStonesStandard: ContinualStonesStandard): ForbiddenRules {
        (rules.find { it is OverlineRule } as? OverlineRule)?.count =
            continualStonesStandard.count
        return ForbiddenRules(rules)
    }
}
