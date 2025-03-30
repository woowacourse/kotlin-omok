package woowacourse.omok.domain.grid

import woowacourse.omok.domain.StoneColor

class Stones {
    private val stones: MutableSet<Stone> = mutableSetOf()

    fun getStonesByColor(stoneColor: StoneColor): Set<Stone> {
        return stones.filter { it.stoneColor == stoneColor }.toSet()
    }

    fun getStoneByPoint(point: Point): Stone? {
        return stones.find { it.point == point }
    }

    fun isSizeEqualTo(standard: Int): Boolean {
        return stones.size == standard
    }

    fun hasMoreStonesThan(
        baseColor: StoneColor,
        comparedColor: StoneColor,
    ): Boolean {
        val baseStones = getStonesByColor(baseColor)
        val comparedStones = getStonesByColor(comparedColor)
        return baseStones.size > comparedStones.size
    }

    operator fun plus(point: Stone) {
        stones.add(point)
    }
}
