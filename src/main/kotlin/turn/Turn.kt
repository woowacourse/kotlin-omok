package turn

abstract class Turn(
    initialState: List<List<Boolean>> = List(15) { List(15) { false } }
) {
    private val _state = initialState.map { it.toMutableList() }.toMutableList()

    val state
        get() = _state.map { it.toList() }.toList()

    fun move(x: Int, y: Int) {

        _state[x - 1][y - 1] = true
    }
}
