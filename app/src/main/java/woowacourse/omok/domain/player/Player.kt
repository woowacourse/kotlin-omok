package woowacourse.omok.domain.player

import woowacourse.omok.domain.rule.adapter.Rule
import woowacourse.omok.domain.rule.lib.type.Violation
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor
import woowacourse.omok.domain.stone.Stones

class Player(
    val stoneColor: StoneColor,
    private val rules: List<Rule>,
) {
    fun violation(
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Violation {
        val violatedRule =
            rules.firstOrNull { rule ->
                rule.violation(playerStones, otherStones, placedStone) != Violation.NONE
            } ?: return Violation.NONE

        return violatedRule.violation(playerStones, otherStones, placedStone)
    }

    fun isWin(
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Boolean = rules.any { rule -> rule.isWin(playerStones, otherStones, placedStone) }
}
