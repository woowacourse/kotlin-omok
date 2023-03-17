package domain

class Stones(value: List<Stone> = listOf()) {
    private val _value: MutableList<Stone> = value.toMutableList()
    val value: List<Stone>
        get() = _value.toList()

    fun place(stone: Stone) {
        _value.add(stone)
    }

    fun validateDuplicatedCoordinate(stone: Stone): Boolean {
        return value.none { it.coordinate == stone.coordinate }
    }
}
