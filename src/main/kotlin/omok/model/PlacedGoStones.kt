package omok.model

class PlacedGoStones(private val _stones: MutableList<GoStone>) {
    val stones: List<GoStone>
        get() = _stones.toList()

    fun addStone(color: GoStoneColor, coordinate: () -> Coordinate) {
        require(canAdd(coordinate)) { "해당 위치에 이미 바둑돌이 있습니다." }
        _stones.add(GoStone(color, coordinate()))
    }

    fun getLastStone(): GoStone = _stones.last()

    private fun canAdd(coordinate: () -> Coordinate): Boolean {
        return !_stones.map { it.coordinate }.contains(coordinate())
    }
}
