package woowacourse.omok.domain.model.stone

class Stones(private val stones: List<Stone>) {
    val value get() = stones.map { it.copy() }

    fun typeStones(stoneType: StoneType): List<Stone> {
        return stones.filter { it.stoneType == stoneType }
            .map { it.copy() }
    }

    fun hasStone(stone: Stone): Boolean = stones.find { it.position == stone.position } != null

    operator fun plus(stone: Stone) = Stones(stones + stone)
}
