package omok.model.stone

class Stones(
    stones: Set<Stone> = setOf(),
    lastStone: Stone? = null,
) {
    private var _stones = stones
    val stones get() = _stones.toSet()

    private var _lastStone: Stone? = lastStone
    val lastStone get() = _lastStone?.copy()

    fun add(stone: Stone) {
        _stones += stone
    }

    fun setLastStone(stone: Stone) {
        _lastStone = stone
    }

//    fun checkWin(
//        otherStones: Stones,
//        lastStone: Stone,
//    ): Boolean = ruleAdapter.checkWin(stones, otherStones.stones, lastStone, 5)
//
//    fun checkAnyFoulCondition(
//        otherStones: Stones,
//        lastStone: Stone,
//    ): FoulCondition = ruleAdapter.checkAnyFoulCondition(stones, otherStones.stones, lastStone)
}
