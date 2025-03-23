package omok.model.adapter

import omok.model.game.FoulCondition
import omok.model.stone.Stone
import rule.facade.BlackRenjuRule

class WhiteRenjuRuleAdapter(
    private val blackRenjuRule: BlackRenjuRule,
) : RuleAdapter {
    override fun checkAnyFoulCondition(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
    ): FoulCondition = FoulCondition.NONE

    override fun checkWin(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
        sameStoneToCheck: Int,
    ): Boolean =
        blackRenjuRule.checkWin(
            whiteStones.toPairList(),
            blackStones.toPairList(),
            startStone.toPair(),
            sameStoneToCheck,
        )
}
