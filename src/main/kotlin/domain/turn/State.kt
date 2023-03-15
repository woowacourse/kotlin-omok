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

    operator fun plus(other: State): State {
        return State(
            this.value.zip(other.value) { row1, row2 ->
                row1.zip(row2) { column1, column2 ->
                    column1 || column2
                }
            }
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as State

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
