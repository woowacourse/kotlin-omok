package woowacourse.omok.domain.stone

import woowacourse.omok.domain.Point

class OmokStones(
    stones: Set<Stone> = emptySet(),
) {
    private val _stones = stones.toMutableSet()
    val stones = _stones.toSet()

    operator fun plus(stone: Stone): OmokStones = OmokStones(_stones + stone)

    fun contains(point: Point): Boolean = _stones.any { it.point == point }
}
