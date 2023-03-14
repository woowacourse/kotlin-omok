package domain

class Stones(value: List<Stone> = listOf()) {
    private val _value: MutableList<Stone> = value.toMutableList()
    val value: List<Stone>
        get() = _value.toList()

    fun place(stone: Stone) {
        require(value.none { it.coordinate.x == stone.coordinate.x && it.coordinate.y == stone.coordinate.y }) {
            MESSAGE_CORRUPT_STONE.format(stone.coordinate.x, stone.coordinate.y)
        }
        _value.add(stone)
    }

    companion object {
        const val MESSAGE_CORRUPT_STONE = "같은 위치에 이미 돌이 있습니다. 위치 : (%d, %d)"
    }
}
