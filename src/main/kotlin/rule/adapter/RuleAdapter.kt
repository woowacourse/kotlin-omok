package rule.adapter

import domain.stone.Stone
import domain.stone.Stones
import rule.lib.OmokRule
import rule.lib.RenjuRule
import rule.lib.type.Violation

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
