package omok.model.rule

import omok.model.rule.count.FiveInRowRule

object OmokRuleManager {
    val winningRule: OmokRule = FiveInRowRule()
    val forbiddenMoveRule: MutableList<OmokRule> = mutableListOf()
}
