package domain.turn

class State(
    initialState: List<List<Boolean>> = List(BOARD_SIZE) { List(BOARD_SIZE) { false } }
) {
    private val _state = initialState.map { it.toMutableList() }.toMutableList()
    val value
        get() = _state.map { it.toList() }.toList()

    fun move(x: Int, y: Int) {
        _state[x - ONE][y - ONE] = true
    }

    companion object {
        private const val BOARD_SIZE = 15
        private const val ONE = 1
    }
}
