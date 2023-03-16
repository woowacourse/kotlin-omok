package domain.board

import domain.State
import domain.Stone

class Board(
    initialState: List<List<State>> = List(BOARD_SIZE) { List(BOARD_SIZE) { State.EMPTY } }
) {
    private val _state = initialState.map { it.toMutableList() }.toMutableList()
    val value
        get() = _state.map { it.toList() }.toList()

    fun move(stone: Stone, state: State) {
        _state[stone.row][stone.column] = state
    }

    fun isEmpty(stone: Stone): Boolean = _state[stone.row][stone.column] == State.EMPTY

    companion object {
        const val BOARD_SIZE = 15
    }
}
