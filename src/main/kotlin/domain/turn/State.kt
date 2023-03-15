package domain.turn

import domain.Stone

class State(
    initialState: List<List<Boolean>> = List(BOARD_SIZE) { List(BOARD_SIZE) { false } }
) {
    private val _state = initialState.map { it.toMutableList() }.toMutableList()
    val value
        get() = _state.map { it.toList() }.toList()

    fun move(stone: Stone) {
        _state[stone.row][stone.column] = true
    }

    fun isEmpty(stone: Stone): Boolean = !_state[stone.row][stone.column]

    companion object {
        private const val BOARD_SIZE = 15
    }
}
