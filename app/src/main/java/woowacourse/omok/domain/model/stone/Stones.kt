package woowacourse.omok.domain.model.stone

import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.position.Stone

class Stones(private val stones: List<Stone>) {
    fun typeStones(stoneType: StoneType): List<Stone> {
        return stones.filter { it.stoneType == stoneType }
            .map { it.copy() }
    }

    fun hasStone(stone: Stone): Boolean = stones.find { it.position == stone.position } != null

    fun find(position: Position): Stone? = stones.find { position == it.position }?.copy()

    operator fun plus(stone: Stone) = Stones(stones + stone)
}
