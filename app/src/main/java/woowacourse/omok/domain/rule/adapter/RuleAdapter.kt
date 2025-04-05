package woowacourse.omok.domain.rule.adapter

import woowacourse.omok.domain.rule.lib.OmokRule
import woowacourse.omok.domain.rule.lib.RenjuRule
import woowacourse.omok.domain.rule.lib.type.Violation
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.Stones

class RuleAdapter(
    private val omokRule: OmokRule,
) : Rule {
    override fun isWin(
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Boolean =
        omokRule.checkWin(
            targetStone = playerStones.value,
            otherStones = otherStones.value,
            startPosition = placedStone.position,
        )

    override fun violation(
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Violation {
        if (omokRule is RenjuRule) {
            return omokRule.checkAnyFoulCondition(
                blackStones = playerStones.value,
                whiteStones = otherStones.value,
                startPosition = placedStone.position,
            )
        }
        return omokRule.checkDuplicatePosition(
            blackStones = playerStones.value,
            whiteStones = otherStones.value,
            curPosition = placedStone.position,
        )
    }
}
