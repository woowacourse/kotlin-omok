package domain.turn

import domain.Stone

abstract class Turn(
    val state: State = State()
) {
    fun move(stone: Stone) {
        state.move(stone)
    }

    fun isEmpty(stone: Stone): Boolean = state.isEmpty(stone)
}
