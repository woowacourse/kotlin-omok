package omok.model

class Board(stones: List<Stone>) {
    private var _stones: MutableList<Stone> = stones.toMutableList()
    val stones: List<Stone>
        get() = _stones

    fun checkOccupiedCoordinate(coordinate: Coordinate): Boolean {
        return stones.none { it.coordinate == coordinate }
    }
}
