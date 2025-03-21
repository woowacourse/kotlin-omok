package omok.domain

import omok.domain.point.OmokPoint

class Stones() {
    private val _stones: MutableSet<OmokPoint> = mutableSetOf()
    val stones: Set<OmokPoint> get() = _stones.deepCopy()

    operator fun plus(point: OmokPoint) {
        _stones.add(point)
    }
}

fun MutableSet<OmokPoint>.deepCopy(): Set<OmokPoint> = map { it.copy() }.toSet()
