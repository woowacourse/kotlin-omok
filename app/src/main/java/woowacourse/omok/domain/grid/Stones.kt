package woowacourse.omok.domain.grid

class Stones {
    private val _stones: MutableSet<Stone> = mutableSetOf()
    val stones: Set<Stone> get() = _stones.deepCopy()

    operator fun plus(point: Stone) {
        _stones.add(point)
    }

    private fun MutableSet<Stone>.deepCopy(): Set<Stone> = map { it.copy() }.toSet()
}
