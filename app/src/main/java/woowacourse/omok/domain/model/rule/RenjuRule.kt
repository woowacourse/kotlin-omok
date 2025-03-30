package woowacourse.omok.domain.model.rule

import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.Stones

interface RenjuRule {
    fun checkWin(
        stones: Stones,
        stone: Stone,
    ): Boolean

    fun canPlace(
        stones: Stones,
        stone: Stone,
    ): RuleResult
}
