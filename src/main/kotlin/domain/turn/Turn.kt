package domain.turn

import domain.Stone

abstract class Turn(
    val state: State = State()
) {
    fun move(stone: Stone) {
        state.move(stone)
    }

    fun canMove(stone: Stone): Boolean = state.canMove(stone)
}
