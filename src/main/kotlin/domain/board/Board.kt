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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Board

        if (_state != other._state) return false

        return true
    }

    override fun hashCode(): Int {
        return _state.hashCode()
    }

    companion object {
        const val BOARD_SIZE = 15
    }
}
