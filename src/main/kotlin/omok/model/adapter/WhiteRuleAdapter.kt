package omok.model.adapter

import rule.OmokRule
import rule.WhiteRenjuRule

class WhiteRuleAdapter: OmokRuleAdapter() {
    override val omokRule: OmokRule = WhiteRenjuRule()
}