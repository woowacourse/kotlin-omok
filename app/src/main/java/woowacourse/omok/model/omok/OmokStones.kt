package woowacourse.omok.model.omok

class OmokStones(private val stones: Map<Position, StoneColor> = emptyMap()) {
    val keys: Set<Position>
        get() = stones.keys

    val entries: Set<Map.Entry<Position, StoneColor>>
        get() = stones.entries

    fun getLastStoneOrNull(): OmokStone? {
        return takeIf { stones.isNotEmpty() }?.let {
            val (position, stoneColor) = stones.entries.last()
            OmokStone(position, stoneColor)
        }
    }

    operator fun plus(stone: OmokStone): OmokStones {
        val newStones = stones.toMutableMap()
        newStones[stone.position] = stone.color
        return OmokStones(newStones)
    }

    operator fun get(position: Position): StoneColor? {
        return stones[position]
    }

    fun isEmptyPosition(position: Position): Boolean = stones.contains(position).not()
}
