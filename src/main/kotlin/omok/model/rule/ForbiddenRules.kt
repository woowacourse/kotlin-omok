package omok.model.rule

import omok.model.rule.library.FourFourRule
import omok.model.rule.library.OmokRule
import omok.model.rule.library.OverlineRule
import omok.model.rule.library.ThreeThreeRule

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
