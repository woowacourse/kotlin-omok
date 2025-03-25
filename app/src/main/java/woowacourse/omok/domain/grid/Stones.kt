package woowacourse.omok.domain.grid

class Stones {
    private val _stones: MutableSet<OmokPoint> = mutableSetOf()
    val stones: Set<OmokPoint> get() = _stones.deepCopy()

    operator fun plus(point: OmokPoint) {
        _stones.add(point)
    }

    private fun MutableSet<OmokPoint>.deepCopy(): Set<OmokPoint> = map { it.copy() }.toSet()
}
