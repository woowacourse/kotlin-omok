package omok.model.stone

import omok.model.adapter.RuleAdapter
import omok.model.game.FoulCondition

class Stones(
    stones: Set<Stone> = setOf(),
    private val ruleAdapter: RuleAdapter,
) {
    private var _stones = stones
    val stones get() = _stones.toSet()

    fun add(stone: Stone) {
        _stones += stone
    }

    fun checkWin(
        otherStones: Stones,
        lastStone: Stone,
    ): Boolean = ruleAdapter.checkWin(stones, otherStones.stones, lastStone, 5)

    fun checkAnyFoulCondition(
        otherStones: Stones,
        lastStone: Stone,
    ): FoulCondition = ruleAdapter.checkAnyFoulCondition(stones, otherStones.stones, lastStone)
}
