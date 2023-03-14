package domain

open class Stones private constructor(values: List<Stone>) {
    private val _values = values.toMutableList()
    val values: List<Stone>
        get() = _values.toList()

    constructor(vararg stones: Stone) : this(stones.toList())

    fun add(stone: Stone) {
        _values.add(stone)
    }
}
