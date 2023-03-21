package domain.stone

import rule.wrapper.point.Point

class Stones(values: List<Stone> = emptyList()) {
    private val _values = values.toMutableList()
    val values: List<Stone>
        get() = _values.toList()

    constructor(vararg stones: Stone) : this(stones.toList())

    fun add(stone: Stone) {
        _values.add(stone)
    }

    fun containsPosition(stone: Stone): Boolean =
        values.asSequence().map { it.point }.contains(stone.point)

    fun getStonesPosition(type: StoneType): List<Point> = values.filter { it.type == type }.map { it.point }

    operator fun plus(stones: Stones): Stones = Stones(values.plus(stones.values))
}
