package rule.adapter

import domain.stone.Stone
import domain.stone.StoneColor
import domain.stone.StoneColor.BLACK
import domain.stone.StoneColor.WHITE
import domain.stone.Stones
import rule.lib.BlackRenjuRule
import rule.lib.WhiteRenjuRule
import rule.lib.type.Violation

class RuleAdapter : Rule {
    private val blackRenjuRule = BlackRenjuRule()
    private val whiteRenjuRule = WhiteRenjuRule()

    override fun isWinByStoneColor(
        stoneColor: StoneColor,
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Boolean =
        when (stoneColor) {
            BLACK -> {
                blackRenjuRule.checkWin(
                    blackStones = playerStones.value,
                    whiteStones = otherStones.value,
                    startPosition = placedStone.position,
                )
            }

            WHITE -> {
                whiteRenjuRule.checkWin(
                    blackStones = emptyList(),
                    whiteStones = playerStones.value,
                    startPosition = placedStone.position,
                )
            }
        }

    override fun violation(
        stoneColor: StoneColor,
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Violation =
        when (stoneColor) {
            BLACK -> {
                blackRenjuRule.checkAnyFoulCondition(
                    blackStones = playerStones.value,
                    whiteStones = otherStones.value,
                    startPosition = placedStone.position,
                )
            }

            WHITE ->
                whiteRenjuRule.checkDuplicatePosition(
                    blackStones = otherStones.value,
                    whiteStones = playerStones.value,
                    curPosition = placedStone.position,
                )
        }
}
