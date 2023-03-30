package omok

class Player(stones: List<Stone> = listOf()) {

    private var _stones = stones
    val stones
        get() = _stones.toList()

    fun put(stone: Stone) {
        _stones = _stones + stone
    }
}
