package omok.model

import omok.model.adapter.FoulCondition
import omok.model.adapter.RuleAdapter

class Stones(
    stones: Set<Stone> = setOf(),
    private val ruleAdapter: RuleAdapter
) {
    private var _stones = stones
    val stones get() = _stones.map { it.copy() }.toSet()

    fun add(stone: Stone) {
        _stones += stone
    }

    fun checkWin(
        otherStones: Stones,
        lastStone: Stone,
    ): Boolean {
        return ruleAdapter.checkWin(stones, otherStones.stones, lastStone, 5)
    }

    fun checkAnyFoulCondition(
        otherStones: Stones,
        lastStone: Stone,
    ): FoulCondition {
        return ruleAdapter.checkAnyFoulCondition(stones, otherStones.stones, lastStone)
    }
}
