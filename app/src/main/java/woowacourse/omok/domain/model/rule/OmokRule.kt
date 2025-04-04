package woowacourse.omok.domain.model.rule

import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.Stones

class OmokRule(private val renjuRule: RenjuRule) : Rule {
    override fun checkWin(
        stones: Stones,
        stone: Stone,
    ): Boolean =
        renjuRule.checkWin(
            stones,
            stone,
        )

    override fun canPlace(
        stones: Stones,
        stone: Stone,
    ): RuleResult = renjuRule.canPlace(stones, stone)
}
