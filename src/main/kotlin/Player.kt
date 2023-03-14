class Player {
    private var _stones = mutableListOf<Stone>()
    val stones
        get() = _stones.toList()

    fun put(stone: Stone) {
        _stones.add(stone)
    }
}
