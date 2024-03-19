package omok.model

class Board(stones: List<Stone>) {
    private var _stones: MutableList<Stone> = stones.toMutableList()
    val stones: List<Stone>
        get() = _stones

    fun checkOccupiedCoordinate(coordinate: Coordinate): Boolean {
        return stones.none { it.coordinate == coordinate }
    }

    fun putStoneOnBoard(
        possibility: Boolean,
        stone: Stone,
    ) {
        when (possibility) {
            true -> _stones.add(stone)
            false -> throw IllegalStateException(ERROR_CANT_PUT_STONE)
        }
    }

    companion object {
        const val ERROR_CANT_PUT_STONE = "이미 돌이 착수된 위치입니다."
    }
}
