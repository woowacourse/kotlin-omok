package omok.model.adapter

import omok.model.Stone
import rule.facade.BlackRenjuRule

class BlackRenjuRuleAdapter(
    private val blackRenjuRule: BlackRenjuRule,
) : RuleAdapter {
    override fun checkAnyFoulCondition(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
    ): FoulCondition {
        return when {
            checkDoubleThreeFoul(blackStones, whiteStones, startStone) -> FoulCondition.DOUBLE_THREE
            checkDoubleFourFoul(blackStones, whiteStones, startStone) -> FoulCondition.DOUBLE_FOUR
            checkOverline(blackStones, startStone) -> FoulCondition.OVERLINE
            else -> FoulCondition.NONE
        }
    }

    override fun checkWin(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
        sameStoneToCheck: Int,
    ): Boolean {
        return checkOmok(blackStones, whiteStones, startStone, sameStoneToCheck)
    }

    private fun checkDoubleThreeFoul(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
    ): Boolean {
        return blackRenjuRule.checkDoubleThreeFoul(
            blackStones.toPairList(),
            whiteStones.toPairList(),
            startStone.toPair(),
        )
    }

    private fun checkDoubleFourFoul(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
    ): Boolean {
        return blackRenjuRule.checkDoubleFourFoul(
            blackStones.toPairList(),
            whiteStones.toPairList(),
            startStone.toPair(),
        )
    }

    private fun checkOverline(
        blackStones: Set<Stone>,
        startStone: Stone,
    ): Boolean {
        return blackRenjuRule.checkOverline(
            blackStones.toPairList(),
            startStone.toPair(),
        )
    }

    private fun checkOmok(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
        sameStoneToCheck: Int,
    ): Boolean {
        return blackRenjuRule.checkWin(
            blackStones.toPairList(),
            whiteStones.toPairList(),
            startStone.toPair(),
            sameStoneToCheck,
        )
    }

    private fun Stone.toPair(): Pair<Int, Int> {
        return point.row to point.col
    }

    private fun Set<Stone>.toPairList(): List<Pair<Int, Int>> {
        return map { it.toPair() }
    }
}
