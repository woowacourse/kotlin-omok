package omok.model

class Stones(
    stones: Set<Stone> = setOf(),
) {
    private var _points = stones
    val points get() = _points.map { it.copy() }.toSet()

    fun add(stone: Stone) {
        _points += stone
    }
}
