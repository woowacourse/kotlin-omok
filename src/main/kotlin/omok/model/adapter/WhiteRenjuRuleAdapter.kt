package omok.model.adapter

import omok.model.game.FoulCondition
import omok.model.stone.Stone
import rule.facade.BlackRenjuRule

class WhiteRenjuRuleAdapter(
    boardWidth: Int = 15,
    boardHeight: Int = 15,
) : RuleAdapter {
    private val blackRenjuRule: BlackRenjuRule = BlackRenjuRule(boardWidth, boardHeight)

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
