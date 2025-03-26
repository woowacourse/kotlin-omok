package domain.player

import domain.stone.Stone
import domain.stone.StoneColor
import domain.stone.Stones
import rule.adapter.Rule
import rule.type.Violation

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
                rule.violation(stoneColor, playerStones, otherStones, placedStone) != Violation.NONE
            } ?: return Violation.NONE

        return violatedRule.violation(stoneColor, playerStones, otherStones, placedStone)
    }

    fun isWin(
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Boolean = rules.any { rule -> rule.isWinByStoneColor(stoneColor, playerStones, otherStones, placedStone) }
}
