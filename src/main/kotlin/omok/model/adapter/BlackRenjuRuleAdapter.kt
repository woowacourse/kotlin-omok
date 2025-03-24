package omok.model.adapter

import omok.model.game.FoulCondition
import omok.model.stone.Stone
import rule.facade.BlackRenjuRule

class BlackRenjuRuleAdapter(
    boardWidth: Int = 15,
    boardHeight: Int = 15,
) : RuleAdapter {
    private val blackRenjuRule: BlackRenjuRule = BlackRenjuRule(boardWidth, boardHeight)

    override fun checkAnyFoulCondition(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
    ): FoulCondition =
        when {
            checkDoubleFourFoul(blackStones, whiteStones, startStone) -> FoulCondition.DOUBLE_FOUR
            checkDoubleThreeFoul(blackStones, whiteStones, startStone) -> FoulCondition.DOUBLE_THREE
            checkOverline(blackStones, startStone) -> FoulCondition.OVERLINE
            else -> FoulCondition.NONE
        }

    override fun checkWin(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
        sameStoneToCheck: Int,
    ): Boolean = checkOmok(blackStones, whiteStones, startStone, sameStoneToCheck)

    private fun checkDoubleThreeFoul(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
    ): Boolean =
        blackRenjuRule.checkDoubleThreeFoul(
            blackStones.toPairList(),
            whiteStones.toPairList(),
            startStone.toPair(),
        )

    private fun checkDoubleFourFoul(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
    ): Boolean =
        blackRenjuRule.checkDoubleFourFoul(
            blackStones.toPairList(),
            whiteStones.toPairList(),
            startStone.toPair(),
        )

    private fun checkOverline(
        blackStones: Set<Stone>,
        startStone: Stone,
    ): Boolean =
        blackRenjuRule.checkOverline(
            blackStones.toPairList(),
            startStone.toPair(),
        )

    private fun checkOmok(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
        sameStoneToCheck: Int,
    ): Boolean =
        blackRenjuRule.checkWin(
            blackStones.toPairList(),
            whiteStones.toPairList(),
            startStone.toPair(),
            sameStoneToCheck,
        )
}
