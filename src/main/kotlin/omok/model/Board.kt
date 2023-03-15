package omok.model

class Board {
    private val _board: List<MutableList<GoStone?>> =
        List(15) { MutableList(15) { null } }
    val board: List<List<GoStone?>>
        get() = _board.map { it.toList() }

    var lastPutCoordinate: Coordinate? = null
        private set

    fun addStone(color: GoStoneColor, getCoordinate: () -> Coordinate) {
        val coordinate = getCoordinate()
        require(canAdd(coordinate)) { "해당 위치에 이미 바둑돌이 있습니다." }

        _board[coordinate.x - 1][coordinate.y - 1] = GoStone(color, coordinate)
        lastPutCoordinate = coordinate
    }

    private fun canAdd(coordinate: Coordinate): Boolean {
        return _board[coordinate.x - 1][coordinate.y - 1] == null
    }

    companion object {
        const val BOARD_LENGTH = 15
    }
}
