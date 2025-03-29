package woowacourse.omok.model.stone

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

    fun currentStoneColor(): StoneColor {
        val lastStoneColor: StoneColor = lastStone?.color ?: return StoneColor.BLACK
        return lastStoneColor.reverse()
    }

    fun setLastStone(stone: Stone) {
        _lastStone = stone
    }

    fun isOccupied(stone: Stone): Boolean {
        val colorReversed = stone.copy(color = stone.color.reverse())
        return stone in stones || colorReversed in stones
    }
}
