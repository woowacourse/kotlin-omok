package omok.model.adapter

import omok.model.Stone
import rule.facade.BlackRenjuRule

class BlackRenjuRuleAdapter(
    private val blackRenjuRule: BlackRenjuRule
) {
    fun checkDoubleThreeFoul(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
    ): Boolean {
        return blackRenjuRule.checkDoubleThreeFoul(
            blackStones.toPairList(),
            whiteStones.toPairList(),
            startStone.toPair()
        )
    }

    fun checkDoubleFourFoul(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
    ): Boolean {
        return blackRenjuRule.checkDoubleFourFoul(
            blackStones.toPairList(),
            whiteStones.toPairList(),
            startStone.toPair()
        )
    }

    fun checkOverline(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
    ): Boolean {
        return blackRenjuRule.checkOverline(
            blackStones.toPairList(),
            startStone.toPair()
        )
    }

    fun checkWin(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
        sameStoneToCheck: Int
    ): Boolean {
        return blackRenjuRule.checkWin(
            blackStones.toPairList(),
            whiteStones.toPairList(),
            startStone.toPair(),
            sameStoneToCheck
        )
    }

    private fun Stone.toPair(): Pair<Int, Int> {
        return point.row to point.col
    }

    private fun Set<Stone>.toPairList(): List<Pair<Int, Int>> {
        return map { it.toPair() }
    }
}