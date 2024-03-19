package omok.model

abstract class StonesState {
    private val _stones: MutableList<Stone> = mutableListOf()
    val stones: List<Stone>
        get() = _stones

    fun checkOccupiedCoordinate(coordinate: Coordinate): Boolean {
        return stones.any { it.coordinate == coordinate }
    }

    fun put(stone: Stone) {
        _stones.add(stone)
    }
}
