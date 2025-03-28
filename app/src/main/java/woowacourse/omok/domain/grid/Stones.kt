package woowacourse.omok.domain.grid

class Stones {
    private val _stone: MutableSet<OmokPoint> = mutableSetOf()
    val stone: Set<OmokPoint> get() = _stone.deepCopy()

    operator fun plus(point: OmokPoint) {
        _stone.add(point)
    }

    private fun MutableSet<OmokPoint>.deepCopy(): Set<OmokPoint> = map { it.copy() }.toSet()
}
