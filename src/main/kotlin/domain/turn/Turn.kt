package domain.turn

abstract class Turn(
    val state: State = State()
) {
    fun move(x: Int, y: Int) {
        state.move(x, y)
    }

    fun canMove(x: Int, y: Int): Boolean = state.canMove(x, y)
}
