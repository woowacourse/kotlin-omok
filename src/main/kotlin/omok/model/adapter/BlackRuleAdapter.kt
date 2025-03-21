package omok.model.adapter

import rule.BlackRenjuRule
import rule.OmokRule

class BlackRuleAdapter: OmokRuleAdapter() {
    override val omokRule: OmokRule = BlackRenjuRule()
}