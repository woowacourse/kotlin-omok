package domain.turn

abstract class Turn(
    val state: State = State()
) {
    fun move(x: Int, y: Int) {
        state.move(x, y)
    }
}
