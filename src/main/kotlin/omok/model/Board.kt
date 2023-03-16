package omok.model

class Board {
    private val _board: List<MutableList<GoStone?>> =
        List(15) { MutableList(15) { null } }
    val board: List<List<GoStone?>>
        get() = _board.map { it.toList() }

    var lastPlacedStone: GoStone? = null
        private set

    fun addStone(color: GoStoneColor, coordinate: Coordinate) {
        lastPlacedStone = GoStone(color, coordinate)
        println("add! : (x,y): (${coordinate.x - 1},${coordinate.y - 1})")
        _board[coordinate.x - 1][coordinate.y - 1] = lastPlacedStone
    }

    fun canAdd(coordinate: Coordinate): Boolean {
        if (_board[coordinate.x - 1][coordinate.y - 1] == null) return true
        else throw IllegalArgumentException("해당 위치에 이미 바둑돌이 있습니다.")
    }

    companion object {
        const val BOARD_LENGTH = 15
    }
}
