class GameBoard {
    private val _stones = mutableListOf<Stone>()
    val stones get() = _stones.toList()

    fun addStone(stone: Stone): Boolean {
        _stones.add(stone)
        return true
    }
}
