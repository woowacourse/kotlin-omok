package woowacourse.omok.domain.rule.adapter

import woowacourse.omok.domain.rule.lib.type.Violation
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.Stones

interface Rule {
    fun isWin(
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Boolean

    fun violation(
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Violation
}
