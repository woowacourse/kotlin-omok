package omok

class Hand {
    private val _stones = mutableListOf<Stone>()
    val stones
        get() = _stones.toList()

    fun add(stone: Stone) {
        _stones.add(stone)
    }
}
